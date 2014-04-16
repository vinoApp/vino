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
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.vino.backend.model.WineAOC;
import com.vino.backend.model.WineDomain;
import com.vino.backend.persistence.Persistor;
import com.vino.backend.persistence.mongo.MongoPersistor;
import com.vino.backend.reference.Reference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static Logger logger = LoggerFactory.getLogger("DBBuilder");

    private static final ImmutableList<AocDB> AOCS_DB = ImmutableList.of(
            AocDB.of("blaye.json", "Blaye"),
            AocDB.of("blaye_cote_de_bordeaux.json", "Blaye-Cotes-de-Bordeaux"),
            AocDB.of("castillon_cotes_de_bordeaux.json", "Cotes-de-Castillon"),
            AocDB.of("cotes_de_blaye.json", "Cotes-de-Blaye"),
            AocDB.of("cotes_de_bourg.json", "Cotes-de-Bourg"),
            AocDB.of("francs_cotes_de_bordeaux.json", "Bordeaux Cotes-de-Francs"),
            AocDB.of("fronsac.json", "Fronsac"),
            AocDB.of("graves.json", "Graves"),
            AocDB.of("lalande_de_pomerol.json", "Lalande de Pomerol"),
            AocDB.of("listrac.json", "Listrac"),
            AocDB.of("lussac_saint_emilion.json", "Lussac Saint-Emilion"),
            AocDB.of("margaux.json", "Margaux"),
            AocDB.of("montagne_saint_emilion.json", "Montagne Saint-Emilion"),
            AocDB.of("moulis.json", "Moulis"),
            AocDB.of("pauillac.json", "Pauillac"),
            AocDB.of("pessac_leognan.json", "Pessac-Léognan"),
            AocDB.of("pomerol.json", "Pomerol"),
            AocDB.of("puisseguin_saint_emilion.json", "Puisseguin Saint-Emilion"),
            AocDB.of("saint_emilion.json", "Saint-Emilion"),
            AocDB.of("saint_estephe.json", "Saint-Estèphe"),
            AocDB.of("saint_julien.json", "Saint-Julien")
    );

    public static void main(String[] args) throws URISyntaxException, IOException {


        FACTORY.getComponent(Name.of(JongoCollection.class, "keys")).get().remove("{ collection: # }", "domains");
        FACTORY.getComponent(Name.of(JongoCollection.class, "domains")).get().drop();
        FACTORY.getComponent(Name.of(JongoCollection.class, "cellar")).get().drop();

        int i = 0;

        for (AocDB aocDB : AOCS_DB) {

            try {

                computeAOC(aocDB);

                System.out.println("==============================================");
                System.out.println(String.format("AOC '%s' : done (%d / %d) !", aocDB.name, ++i, AOCS_DB.size()));
                System.out.println("==============================================");

            } catch (Exception e) {
                logger.error("Error during building domain : ", e);
            }
        }
    }

    private static void computeAOC(AocDB aocDB) throws URISyntaxException, IOException {

        WineAOC aoc = AOCS.get().findOne("{ name : # }", aocDB.name).as(WineAOC.class);
        if (aoc == null) {
            throw new IllegalStateException("AOC not found : " + aocDB.name);
        }

        URL fromResource = Resources.getResource("domains/fromSrv/" + aocDB.file);
        File srcFile = new File(fromResource.toURI());

        List<WineDBRecord> records = MAPPER.readValue(srcFile,
                MAPPER.getTypeFactory().constructCollectionType(List.class, WineDBRecord.class));

        int j = 0;

        for (WineDBRecord record : records) {

            WineDomain domain = new WineDomain();
            domain.setName(record.getName());
            domain.setOrigin(Reference.of(aoc));

            computeSticker(record, domain);
            populateWithDetails(record, domain);

            PERSISTOR.persist(domain);

            System.out.println(String.format("Computed (%s) : %s (%d / %d)", aocDB.name, record.getId(), ++j, records.size()));
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

    private static class AocDB {

        public String file;

        public String name;

        public static AocDB of(String file, String name) {
            AocDB aocDB = new AocDB();
            aocDB.file = file;
            aocDB.name = name;
            return aocDB;
        }

    }

}
