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

package com.vino.backend.crawling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * User: eoriou
 * Date: 09/03/2014
 * Time: 10:47
 */
public class WineDomainsCIVBCrawler implements WineDomainsCrawler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String WINE_COLOR = "rouge";

    private static final String[] AOCS = new String[]{
            "Pomerol",
            "Margaux",
            "Moulis",
            "Listrac-M%C3%A9doc",
            "Saint-Julien",
            "Pauillac",
            "Saint-Est%C3%A8phe",
            "Fronsac",
            "Pomerol",
            "Lalande%20de%20Pomerol",
            "Saint-Emilion",
            "Saint-Emilion%20Grand%20Cru",
            "Montagne%20Saint%20Emilion",
            "Lussac%20Saint%20Emilion",
            "Puisseguin%20Saint%20Emilion",
            "Castillon-C%C3%B4tes%20de%20Bordeaux",
            "Francs-C%C3%B4tes%20de%20Bordeaux%20" + WINE_COLOR,
            "Blaye%20" + WINE_COLOR,
            "C%C3%B4tes%20de%20Blaye",
            "Blaye%20C%C3%B4tes%20de%20Bordeaux%20" + WINE_COLOR + "%20ou%20Premi%C3%A8res%20C%C3%B4tes%20de%20Blaye%20" + WINE_COLOR,
            "C%C3%B4tes%20de%20Bourg%20" + WINE_COLOR,
            "Graves%20" + WINE_COLOR,
            "Pessac-L%C3%A9ognan%20" + WINE_COLOR,
            "C%C3%A9rons"
    };

    public void extractRecords() throws JsonProcessingException, InterruptedException {

        final List<Map<String, WineDomainCrawledRecord>> maps =
                Collections.synchronizedList(new ArrayList<Map<String, WineDomainCrawledRecord>>());

        final CountDownLatch latch = new CountDownLatch(AOCS.length);

        for (final String aoc : AOCS) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, WineDomainCrawledRecord> records = new HashMap<>();
                    maps.add(records);

                    try {

                        for (int i = 0; i < 100; i++) {
                            makeHttpRequest(records, aoc);
                            System.out.println(aoc + " : " + i + "%");
                        }

                        latch.countDown();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        }

        latch.await();

        for (Map<String, WineDomainCrawledRecord> map : maps) {
            System.out.println("=============================================");
            System.out.println(MAPPER.writeValueAsString(map.values()));
            System.out.println(map.size());
        }
    }

    private void makeHttpRequest(Map<String, WineDomainCrawledRecord> records, String aoc) throws IOException {

        // Test - Pessac-Léognan
        HttpRequest post = HttpRequest.post("http://www.smart-bordeaux.fr/civb/search/advanced")
                .send("aoc=" + aoc + "&lang=fr_FR");

        String response = post.body();

        List<WineDomainCrawledRecord> rcvdRecords = MAPPER.readValue(response,
                MAPPER.getTypeFactory().constructCollectionType(List.class, WineDomainCrawledRecord.class));

        for (WineDomainCrawledRecord record : rcvdRecords) {
            records.put(record.getId(), record);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        new WineDomainsCIVBCrawler().extractRecords();
    }
}
