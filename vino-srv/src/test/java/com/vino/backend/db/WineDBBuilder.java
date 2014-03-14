/*
 * Copyright 2013 - Elian ORIOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vino.backend.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.io.Resources;
import com.vino.backend.model.WineAOC;
import com.vino.backend.model.WineDomain;
import com.vino.backend.persistence.Persistor;
import com.vino.backend.persistence.mongo.MongoCollections;
import com.vino.backend.persistence.mongo.MongoPersistor;
import com.vino.backend.reference.Reference;
import org.jongo.MongoCollection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import restx.factory.Factory;
import restx.factory.Name;
import restx.jongo.JongoCollection;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * User: eoriou
 * Date: 09/03/2014
 * Time: 12:02
 */
public class WineDBBuilder {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final Factory FACTORY = Factory.builder().addFromServiceLoader().build();
    private static final JongoCollection AOCS = FACTORY
            .getComponent(Name.of(JongoCollection.class, "aocs"));
    private static final Persistor PERSISTOR = FACTORY.getComponent(Name.of(MongoPersistor.class));

    private static final String FILE_NAME = "saint_emilion.json";
    private static final String AOC_NAME = "Saint-Emilion";

    public static void main(String[] args) throws URISyntaxException, IOException {

        FACTORY.getComponent(Name.of(JongoCollection.class, "domains")).get().drop();
        FACTORY.getComponent(Name.of(JongoCollection.class, "cellar")).get().drop();

        WineAOC aoc = AOCS.get().findOne("{ name : # }", AOC_NAME).as(WineAOC.class);
        if (aoc == null) {
            throw new IllegalStateException("AOC not found : " + AOC_NAME);
        }

        URL fromResource = Resources.getResource("domains/fromSrv/" + FILE_NAME);
        File srcFile = new File(fromResource.toURI());

        List<WineDBRecord> records = MAPPER.readValue(srcFile,
                MAPPER.getTypeFactory().constructCollectionType(List.class, WineDBRecord.class));

        int i = 0;

        for (WineDBRecord record : records) {

            WineDomain domain = new WineDomain();
            domain.setName(record.getName());
            domain.setOrigin(Reference.of(aoc));

            computeSticker(record, domain);
            populateWithDetails(record, domain);

            PERSISTOR.persist(domain);

            System.out.println(String.format("Computed : %s (%d / %d)", record.getId(), ++i, records.size()));
        }
    }

    private static void computeSticker(WineDBRecord record, WineDomain domain) {

        if (record.getUrlpicture() == null) {
            return;
        }

        HttpRequest get = HttpRequest.get(record.getUrlpicture());
        domain.setSticker(get.bytes());
    }

    private static void populateWithDetails(WineDBRecord record, WineDomain domain) throws IOException {

        HttpRequest get = HttpRequest.post("http://www.smart-bordeaux.com/civb/wine/" + record.getId())
                .send("lang=fr_FR&front=true");

        WineDBRecord rcvdRecord = MAPPER.readValue(get.body(), WineDBRecord.class);
        String html = rcvdRecord.getHtml();
        if (html == null) {
            return;
        }
        Document doc = Jsoup.parse(html);

        // Wine
        WineDomain.Wine wine = new WineDomain.Wine();
        wine.floor = doc.select("#wine-origin_floor").text();
        wine.grape = doc.select("#wine-cuvee").text();
        wine.medal = doc.select("#wine-medal").text();

        // Tasting
        WineDomain.Tasting tasting = new WineDomain.Tasting();
        tasting.comment = doc.select("#wine-tasting_comment").text();
        tasting.garde = doc.select("#wine-storage_duration").text();
        tasting.temperature = doc.select("#wine-serving_temperature").text();

        // History
        WineDomain.History history = new WineDomain.History();
        history.description = doc.select("#wine-history").text();

        // Localization
        String localization = doc.select("#wine-gps a").attr("href");

        domain.setLocalization(localization);
        domain.setWineDescription(wine);
        domain.setHistory(history);
        domain.setTasting(tasting);
    }

}
