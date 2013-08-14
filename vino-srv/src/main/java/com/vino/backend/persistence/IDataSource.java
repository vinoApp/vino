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

import com.vino.backend.model.WineBottle;
import com.vino.backend.model.cellar.WineCellarRecord;
import com.vino.backend.model.origins.WineAOC;
import com.vino.backend.model.origins.WineDomain;

import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:37 PM
 */
public interface IDataSource {

    void resetDB();

    /////////////////////////////
    // DATA RETRIEVING
    /////////////////////////////

    List<WineBottle> getAllKnownWineBottles();

    List<WineCellarRecord> getAllWineBottlesInCellar();

    List<WineAOC> getAllAOCs();

    WineAOC getAOCByID(int id);

    List<WineDomain> getAllWineDomains();

    WineDomain getDomainById(int id);

    WineBottle getBottleByBarCode(String barcode);

    WineBottle getBottleById(int id);

    /////////////////////////////
    // DATA PERSISTENCE
    /////////////////////////////

    boolean addWineBottleToKnown(WineBottle bottle);

    boolean updateKnownWineBottle(int bottleID, WineBottle newBottle);

    boolean removeWineBottleFromKnown(int id);

    boolean addWineDomain(WineDomain domain);

    boolean removeWineDomain(int id);

    boolean loadBottleInCellar(int id, int qty);

    boolean unloadBottleInCellar(int id, int qty);

    /////////////////////////////
    /// STATS
    /////////////////////////////

    // TODO
    // - get number of bottles per region and per aoc
    // - get frequency of registering and unregistering (per week, per month, per year)

}
