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
        Reference<WineRegion> medocRef = Reference.of(medoc);

        WineRegion libournais = new WineRegion().setName("Libournais");
        PERSISTOR.persist(libournais);
        Reference<WineRegion> libournaisRef = Reference.of(libournais);

        WineRegion blayais = new WineRegion().setName("Blayais");
        PERSISTOR.persist(blayais);
        Reference<WineRegion> blayaisRef = Reference.of(blayais);

        WineRegion graves = new WineRegion().setName("Graves");
        PERSISTOR.persist(graves);
        Reference<WineRegion> gravesRef = Reference.of(graves);


        // AOCS
        addAOC("Margaux", medocRef);
        addAOC("Moulis", medocRef);
        addAOC("Listrac", medocRef);
        addAOC("Saint-Julien", medocRef);
        addAOC("Pauillac", medocRef);
        addAOC("Saint-Estèphe", medocRef);

        addAOC("Fronsac", libournaisRef);
        addAOC("Pomerol", libournaisRef);
        addAOC("Lalande de Pomerol", libournaisRef);
        addAOC("Saint-Emilion", libournaisRef);
        addAOC("Montagne Saint-Emilion", libournaisRef);
        addAOC("Lussac Saint-Emilion", libournaisRef);
        addAOC("Puisseguin Saint-Emilion", libournaisRef);
        addAOC("Cotes-de-Castillon", libournaisRef);
        addAOC("Bordeaux Cotes-de-Francs", libournaisRef);

        addAOC("Blaye", blayaisRef);
        addAOC("Cotes-de-Blaye", blayaisRef);
        addAOC("Blaye-Cotes-de-Bordeaux", blayaisRef);
        addAOC("Cotes-de-Bourg", blayaisRef);

        addAOC("Graves", gravesRef);
        addAOC("Graves Superieurs", gravesRef);
        addAOC("Pessac-Léognan", gravesRef);
        addAOC("Cérons", gravesRef);
    }

    private void addAOC(String name, Reference<WineRegion> regionRef) {
        WineAOC aoc = new WineAOC()
                .setName(name)
                .setRegion(regionRef);
        PERSISTOR.persist(aoc);
    }
}
