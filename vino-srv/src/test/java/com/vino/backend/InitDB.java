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

import com.vino.backend.model.*;
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

    private int nextBarCode = 100000;

    @BeforeClass
    public static void init() throws Exception {
        FACTORY.getComponent(Name.of(Jongo.class)).getDatabase().dropDatabase();
    }

    private Reference<WineAOC> addAOC(String name, Reference<WineRegion> regionRef) {
        WineAOC aoc = new WineAOC()
                .setName(name)
                .setRegion(regionRef);
        PERSISTOR.persist(aoc);
        return Reference.of(aoc);
    }

    private Reference<WineDomain> addDomain(String name, Reference<WineAOC> aoc, byte[] sticker) {
        WineDomain domain = new WineDomain()
                .setName(name)
                .setOrigin(aoc)
                .setSticker(sticker);
        PERSISTOR.persist(domain);
        return Reference.of(domain);
    }

    private Reference<WineBottle> addBottle(Reference<WineDomain> domainRef, int vintage) {
        WineBottle bottle = new WineBottle()
                .setCode(Barcode.ean13("0000000" + (++nextBarCode)))
                .setDomain(domainRef)
                .setVintage(vintage);
        PERSISTOR.persist(bottle);
        return Reference.of(bottle);
    }

    private void addCellarRecord(Reference<WineBottle> bottleRef, int qty) {
        PERSISTOR.addInCellar(bottleRef, qty);
    }

    @Test
    public void populate() {

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
        Reference<WineAOC> margauxRef = addAOC("Margaux", medocRef);
        addAOC("Moulis", medocRef);
        addAOC("Listrac", medocRef);
        Reference<WineAOC> saintJulienRef = addAOC("Saint-Julien", medocRef);
        addAOC("Pauillac", medocRef);
        addAOC("Saint-Estèphe", medocRef);

        addAOC("Fronsac", libournaisRef);
        Reference<WineAOC> pomerolRef = addAOC("Pomerol", libournaisRef);
        addAOC("Lalande de Pomerol", libournaisRef);
        Reference<WineAOC> saintEmilionRef = addAOC("Saint-Emilion", libournaisRef);
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
        Reference<WineAOC> pessacLeoganRef = addAOC("Pessac-Léognan", gravesRef);
        addAOC("Cérons", gravesRef);

        // Domains
        Reference<WineDomain> hbRef = addDomain("Mission Haut-Brion", pessacLeoganRef, null);
        Reference<WineDomain> mrgRef = addDomain("Chateau Margaux", margauxRef, null);
        Reference<WineDomain> beychevellRef = addDomain("Chateau Beychevelle", saintJulienRef, null);
        Reference<WineDomain> cbRef = addDomain("Chateau Cheval Blanc", saintEmilionRef, null);
        Reference<WineDomain> petrusRef = addDomain("Chateau Pétrus", pomerolRef, null);

        // Bottles
        Reference<WineBottle> hb2008Ref = addBottle(hbRef, 2008);
        Reference<WineBottle> hb2009Ref = addBottle(hbRef, 2009);
        Reference<WineBottle> hb2010Ref = addBottle(hbRef, 2010);

        Reference<WineBottle> mrg2008Ref = addBottle(mrgRef, 2008);
        Reference<WineBottle> mrg2010Ref = addBottle(mrgRef, 2010);
        Reference<WineBottle> mrg2011Ref = addBottle(mrgRef, 2011);

        Reference<WineBottle> petrus2000Ref = addBottle(petrusRef, 2000);
        Reference<WineBottle> petrus2005Ref = addBottle(petrusRef, 2005);

        // Cellar records
        addCellarRecord(hb2008Ref, 10);
        addCellarRecord(hb2008Ref, 10);

        addCellarRecord(petrus2005Ref, 10);
        addCellarRecord(petrus2005Ref, 10);

        addCellarRecord(mrg2008Ref, 5);
        addCellarRecord(mrg2011Ref, 10);
    }
}
