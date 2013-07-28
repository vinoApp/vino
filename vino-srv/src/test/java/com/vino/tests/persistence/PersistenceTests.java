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

package com.vino.tests.persistence;

import com.vino.backend.persistence.DataSourcesBundle;
import org.junit.Test;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 7:35 PM
 */
public class PersistenceTests {

    @Test
    public void testGetAllKnownWineBottles() {
        DataSourcesBundle.getInstance().getDefaultDataSource().getAllKnownWineBottles();
    }

    @Test
    public void testGetAllWineBottlesInCellar() {
        DataSourcesBundle.getInstance().getDefaultDataSource().getAllWineBottlesInCellar();
    }

    @Test
    public void testGetAllAOCS() {
        DataSourcesBundle.getInstance().getDefaultDataSource().getAllAOCs();
    }

    @Test
    public void testGetAllAOCSWithRegionID() {
        DataSourcesBundle.getInstance().getDefaultDataSource().getAllAOCs(2);
    }

    @Test
    public void testGetAllWineDomains() {
        DataSourcesBundle.getInstance().getDefaultDataSource().getAllWineDomains();
    }
}
