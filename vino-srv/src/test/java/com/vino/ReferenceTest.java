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

package com.vino;

import com.google.common.base.Optional;
import com.vino.domain.Reference;
import com.vino.domain.WineAOC;
import com.vino.domain.WineDomain;
import com.vino.domain.WineRegion;
import com.vino.persistence.Persistor;
import com.vino.persistence.mongo.MongoPersistor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import restx.factory.Factory;
import restx.factory.Name;
import restx.jongo.JongoCollection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User: walien
 * Date: 1/8/14
 * Time: 12:48 AM
 */
public class ReferenceTest {

    private static final Factory FACTORY = Factory.builder().addFromServiceLoader().build();

    private static final Persistor PERSISTOR = FACTORY.getComponent(Name.of(MongoPersistor.class));

    @BeforeClass
    public static void init() {
        Reference.setPersistor(PERSISTOR);
    }

    @AfterClass
    public static void finish() {
        Reference.cleanPersistor();
    }

    @Test
    public void should_properly_retrieve_linked_region() {

        JongoCollection aocs = FACTORY.queryByName(Name.of(JongoCollection.class, "aocs")).findOneAsComponent().get();

        WineAOC pessacLeognan = aocs.get().findOne(" { name : # } ", "Pessac-Léognan").as(WineAOC.class);

        Optional<WineRegion> wineRegion = pessacLeognan.getRegion().get();

        assertThat(wineRegion).isNotNull();
        assertThat(wineRegion.isPresent()).isTrue();
        assertThat(wineRegion.get().getName()).isEqualTo("Graves");
    }

    @Test
    public void should_properly_retrieve_linked_origins() {

        JongoCollection aocs = FACTORY.queryByName(Name.of(JongoCollection.class, "domains")).findOneAsComponent().get();

        WineDomain domain = aocs.get().findOne(" { name : # } ", "Château Larrivet Haut-Brion").as(WineDomain.class);

        Optional<WineAOC> wineAOC = domain.getOrigin().get();

        assertThat(wineAOC).isNotNull();
        assertThat(wineAOC.isPresent()).isTrue();
        assertThat(wineAOC.get().getName()).isEqualTo("Pessac-Léognan");

        Optional<WineRegion> wineRegion = wineAOC.get().getRegion().get();

        assertThat(wineRegion).isNotNull();
        assertThat(wineRegion.isPresent()).isTrue();
        assertThat(wineRegion.get().getName()).isEqualTo("Graves");
    }

}
