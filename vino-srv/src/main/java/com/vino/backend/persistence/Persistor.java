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

package com.vino.backend.persistence;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.vino.backend.model.*;
import com.vino.backend.reference.Reference;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:37 PM
 */
public interface Persistor {

    ///////////////////////////////////
    // DATA ACCESS
    ///////////////////////////////////

    Optional<EntityKey> getEntityKey(String key);

    <T extends Entity> Optional<T> getEntity(String key);

    ImmutableList<WineAOC> getAllAOCS();

    ImmutableList<WineRegion> getAllRegions();

    ImmutableList<WineDomain> getAllDomains();

    ImmutableList<WineBottle> getAllBottles();

    WineCellar getCellar();

    ImmutableList<WineCellar.Record> getRecordsByDomain(String domainKey);

    Optional<WineCellar.Record> getRecord(String bottleKey);

    ///////////////////////////////////
    // DATA PERSISTENCE
    ///////////////////////////////////

    boolean persist(WineAOC aoc);

    boolean persist(WineRegion region);

    boolean persist(WineDomain domain);

    boolean persist(WineBottle bottle);

    boolean addInCellar(Reference<WineBottle> bottle, int quantity);

    boolean removeFromCellar(Reference<WineBottle> bottle, int quantity);

    ///////////////////////////////////
    // DATA DELETION
    ///////////////////////////////////

    boolean delete(String key);

}
