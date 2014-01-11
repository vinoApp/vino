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

package com.vino.backend;

import com.vino.backend.model.WineAOC;
import com.vino.backend.model.WineRegion;
import com.vino.backend.persistence.Persistor;
import com.vino.backend.persistence.mongo.MongoPersistor;
import com.vino.backend.reference.Reference;
import org.jongo.Jongo;
import org.junit.BeforeClass;
import org.junit.Test;
import restx.factory.Factory;
import restx.factory.Name;

/**
 * User: walien
 * Date: 1/8/14
 * Time: 12:48 AM
 */
public class InitDB {

    private static final Factory FACTORY = Factory.builder().addFromServiceLoader().build();
    private static final Persistor PERSISTOR = FACTORY.getComponent(Name.of(MongoPersistor.class));

    @BeforeClass
    public static void init() throws Exception {
        FACTORY.getComponent(Name.of(Jongo.class)).getDatabase().dropDatabase();
    }

    @Test
    public void populateWithOrigins() {

        // Regions
        WineRegion medoc = new WineRegion().setName("Médoc");
        PERSISTOR.persist(medoc);

        WineRegion libournais = new WineRegion().setName("Libournais");
        PERSISTOR.persist(libournais);

        WineRegion blayais = new WineRegion().setName("Blayais");
        PERSISTOR.persist(blayais);

        WineRegion graves = new WineRegion().setName("Graves");
        PERSISTOR.persist(graves);

        // AOCS
        WineAOC pessacLeognan = new WineAOC()
                .setName("Pessac-Léognan")
                .setRegion(new Reference<WineRegion>(graves.getKey()));
        PERSISTOR.persist(pessacLeognan);

    }

}
