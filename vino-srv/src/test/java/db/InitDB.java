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

package db;

import com.vino.backend.persistence.mongo.MongoCollections;
import model.WineRegion;
import org.jongo.Jongo;
import org.junit.BeforeClass;
import org.junit.Test;
import restx.factory.Factory;
import restx.factory.Name;
import restx.jongo.JongoCollection;

/**
 * User: walien
 * Date: 1/8/14
 * Time: 12:48 AM
 */
public class InitDB {

    private static final Factory FACTORY = Factory.builder().addFromServiceLoader().build();

    @BeforeClass
    public static void init() throws Exception {
        FACTORY.getComponent(Name.of(Jongo.class)).getDatabase().dropDatabase();
    }

    @Test
    public void populateWithRegions() {

        JongoCollection regions = FACTORY.queryByName(Name.of(JongoCollection.class, MongoCollections.REGIONS.get())).findOneAsComponent().get();

        regions.get().insert(new WineRegion().setName("Médoc"));
        regions.get().insert(new WineRegion().setName("Libournais"));
        regions.get().insert(new WineRegion().setName("Blayais"));
        regions.get().insert(new WineRegion().setName("Graves"));
    }

}
