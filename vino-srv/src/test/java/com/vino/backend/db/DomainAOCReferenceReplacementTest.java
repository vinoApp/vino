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
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.sun.istack.internal.Nullable;
import com.vino.backend.model.WineAOC;
import com.vino.backend.model.WineDomain;
import com.vino.backend.reference.Reference;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.factory.Factory;
import restx.factory.Name;
import restx.jongo.JongoCollection;


public class DomainAOCReferenceReplacementTest {

    public static final Factory FACTORY = Factory.builder().addFromServiceLoader().build();
    private static final JongoCollection AOCS = FACTORY.getComponent(Name.of(JongoCollection.class, "aocs"));
    private static final JongoCollection DOMAINS = FACTORY.getComponent(Name.of(JongoCollection.class, "domains"));

    private static final ImmutableMap<String, String> REPLACEMENT_MAPPING = ImmutableMap.<String, String>builder()
//            .put("5323574c0364cbcb5859768e", "Saint-Estèphe")
//            .put("5323574c0364cbcb58597691", "Lalande de Pomerol")
//            .put("5323574c0364cbcb58597690", "Pomerol")
//            .put("5323574c0364cbcb5859769e", "Pessac-Léognan")
//            .put("5323574c0364cbcb5859768f", "Fronsac")
//            .put("5323574c0364cbcb5859768c", "Saint-Julien")
//            .put("5323574c0364cbcb58597693", "Montagne Saint-Emilion")
//            .put("5323574c0364cbcb58597692", "Saint-Emilion")
//            .put("5323574c0364cbcb5859768d", "Pauillac")
//            .put("5323574c0364cbcb5859768a", "Moulis")
            .put("5323574c0364cbcb5859768b", "Listrac")
//            .put("5323574c0364cbcb58597689", "Margaux")
//            .put("5323574c0364cbcb58597698", "Blaye")
//            .put("5323574c0364cbcb58597699", "Blaye-Cotes-de-Bordeaux")
//            .put("5323574c0364cbcb5859769c", "Graves")
//            .put("5323574c0364cbcb58597694", "Lussac Saint-Emilion")
//            .put("5323574c0364cbcb58597695", "Puisseguin Saint-Emilion")
            .put("5323574c0364cbcb5859769b", "Cotes-de-Bourg")
//            .put("5323574c0364cbcb5859769a", "Cotes-de-Blaye")
//            .put("5323574c0364cbcb58597696", "Cotes-de-Castillon")
//            .put("5323574c0364cbcb58597697", "Bordeaux Cotes-de-Francs")
            .build();

    private final Logger logger = LoggerFactory.getLogger(DomainAOCReferenceReplacementTest.class);

    @Test
    public void should_replace_domain_old_origin_ref_with_brand_new_ones() throws Exception {

        // Load all aocs
        ImmutableList<WineAOC> allAocs = ImmutableList.copyOf(AOCS.get().find().as(WineAOC.class));

        // Makes all domains pointing to right AOC in DB
        Iterable<WineDomain> dbDomains = DOMAINS.get().find().as(WineDomain.class);
        for (WineDomain dbDomain : dbDomains) {

            final String domainAocName = REPLACEMENT_MAPPING.get(dbDomain.getOrigin().getKey());
            if (domainAocName == null) {
                logger.warn("No mapping found for old aoc key", dbDomain.getOrigin().getKey());
                continue;
            }

            Optional<WineAOC> dbAoc = Iterables.tryFind(allAocs, new Predicate<WineAOC>() {
                @Override
                public boolean apply(@Nullable WineAOC input) {
                    return input.getName().equalsIgnoreCase(domainAocName);
                }
            });

            if (!dbAoc.isPresent()) {
                logger.warn("AOC {} not found in DB... Perhaps a typo ??!", domainAocName);
                continue;
            }

            dbDomain.setOrigin(Reference.of(dbAoc.get()));
            DOMAINS.get().save(dbDomain);
            logger.info("Domain {} updated !", dbDomain.getName());
        }
    }
}
