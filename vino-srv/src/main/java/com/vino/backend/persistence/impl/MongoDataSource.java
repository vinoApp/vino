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

package com.vino.backend.persistence.impl;

import com.vino.backend.model.WineBottle;
import com.vino.backend.model.origins.WineAOC;
import com.vino.backend.model.origins.WineDomain;
import com.vino.backend.persistence.IDataSource;

import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:38 PM
 */
public class MongoDataSource implements IDataSource {

    @Override
    public List<WineBottle> getAllKnownWineBottles() {
        return null;
    }

    @Override
    public List<WineBottle> getAllWineBottlesInCellar() {
        return null;
    }

    @Override
    public List<WineAOC> getAllAOCs() {
        return null;
    }

    @Override
    public List<WineAOC> getAllAOCs(int regionID) {
        return null;
    }

    @Override
    public List<WineDomain> getAllWineDomains() {
        return null;
    }

    @Override
    public boolean registerWineDomain(WineDomain domain) {
        return false;
    }

    @Override
    public boolean unregisterWineDomain(int id) {
        return false;
    }

    @Override
    public boolean registerWineBottle(WineBottle bottle, int qty) {
        return false;
    }

    @Override
    public boolean unregisterWineBottle(int id, int qty) {
        return false;
    }
}


