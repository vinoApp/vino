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

    // Origins

    Iterable<WineAOC> getAllAOCS();

    Iterable<WineRegion> getAllRegions();

    Iterable<WineDomain> getAllDomains();

    Iterable<WineDomain> getDomainsByAOC(String aocKey);

    // Cellars

    Iterable<WineCellar> getAllCellars();

    // Cellar content

    Iterable<WineCellarRecord> getAllRecords(Reference<WineCellar> cellar);

    Iterable<WineCellarRecord> getRecordsByDomain(Reference<WineDomain> domain);

    Optional<WineCellarRecord> getRecord(Reference<WineCellar> cellar, Reference<WineDomain> domain, int vintage);

    Optional<WineCellarRecord> getRecordByBarCode(Reference<WineCellar> cellar, String barcode);

    ///////////////////////////////////
    // DATA PERSISTENCE
    ///////////////////////////////////

    boolean persist(WineAOC aoc);

    boolean persist(WineRegion region);

    boolean persist(WineDomain domain);

    boolean persist(WineCellar cellar);

    boolean persist(WineCellarRecord record);

    boolean persist(Movement movement);

    boolean addInCellar(Reference<WineCellar> cellar, WineCellarRecord record, int quantity);

    boolean removeFromCellar(String id, int quantity);

    ///////////////////////////////////
    // DATA DELETION
    ///////////////////////////////////

    boolean delete(String key);
}
