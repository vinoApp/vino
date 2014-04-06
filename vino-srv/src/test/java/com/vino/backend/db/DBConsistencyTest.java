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
import com.vino.backend.model.EntityKey;
import com.vino.backend.model.WineDomain;
import com.vino.backend.persistence.Persistor;
import com.vino.backend.persistence.mongo.MongoPersistor;
import org.junit.Test;
import restx.factory.Factory;
import restx.factory.Name;
import restx.jongo.JongoCollection;

import java.util.List;

public class DBConsistencyTest {

    private static final Factory FACTORY = Factory.builder().addFromServiceLoader().build();
    private static final Persistor PERSISTOR = FACTORY.getComponent(Name.of(MongoPersistor.class));

    private static final List<WineDomain> DOMAINS = PERSISTOR.getAllDomains();

    @Test
    public void domains_should_appears_in_keys() throws Exception {

        for (WineDomain domain : DOMAINS) {

            Optional<EntityKey> domainKey = PERSISTOR.getEntityKey(domain.getKey());
            if (!domainKey.isPresent()) {

            }
        }
    }
}
