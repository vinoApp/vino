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

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.vino.backend.model.EntityKey;
import com.vino.backend.model.WineAOC;
import com.vino.backend.model.WineDomain;
import com.vino.backend.persistence.Persistor;
import com.vino.backend.persistence.mongo.MongoPersistor;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.factory.Factory;
import restx.factory.Name;
import restx.jongo.JongoCollection;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DBConsistencyTest {

    private final Logger logger = LoggerFactory.getLogger(DomainAOCReferenceReplacementTest.class);


    private static final Factory FACTORY = Factory.builder().addFromServiceLoader().build();
    private static final Persistor PERSISTOR = FACTORY.getComponent(Name.of(MongoPersistor.class));

    private static final Iterable<WineDomain> DOMAINS = PERSISTOR.getAllDomains();
    private static final Iterable<WineAOC> AOCS = PERSISTOR.getAllAOCS();
    private static final JongoCollection KEYS = FACTORY.getComponent(Name.of(JongoCollection.class, "keys"));

    @Test
    public void domains_should_appears_in_keys() throws Exception {

        for (WineDomain domain : DOMAINS) {

            Optional<EntityKey> domainKey = PERSISTOR.getEntityKey(domain.getKey());
            if (!domainKey.isPresent()) {
                KEYS.get().insert(new EntityKey(domain.getKey(), "domains"));
            }
        }
    }

    @Test
    public void retrieve_right_aocs() throws Exception {

        Multimap<String, String> domains = HashMultimap.create();
        for (WineDomain domain : DOMAINS) {
            String aocKey = domain.getOrigin().getKey();
            if (domains.get(aocKey).size() <= 10) {
                domains.put(aocKey, domain.getName());
            }
        }
        for (Map.Entry<String, Collection<String>> entry : domains.asMap().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

    @Test
    public void check_aocs_consistency() {

        for (WineAOC aoc : AOCS) {

            if (KEYS.get().count("{_id: #}", new ObjectId(aoc.getKey())) <= 0) {
                logger.error("AOC is missing into the keys collection : {} ({})", aoc.getName(), aoc.getKey());
            }
        }
    }
}
