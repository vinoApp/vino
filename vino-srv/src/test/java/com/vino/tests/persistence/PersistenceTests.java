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

import com.vino.backend.model.WineBottle;
import com.vino.backend.model.origins.WineDomain;
import com.vino.backend.persistence.DataSourcesBundle;
import com.vino.backend.tools.ImageUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 7:35 PM
 */
public class PersistenceTests {

    @BeforeClass
    public static void resetDB() {
        DataSourcesBundle.getInstance().getDefaultDataSource().resetDB();
    }

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
    public void testGetAllWineDomains() {
        DataSourcesBundle.getInstance().getDefaultDataSource().getAllWineDomains();
    }

    @Test
    public void testAddBottleToKnownOnes() {
        WineDomain domain = DataSourcesBundle.getInstance().getDefaultDataSource().getDomainById(2);

        WineBottle bottle = new WineBottle("3607345750309", 2008, domain);
        try {
            BufferedImage img = ImageIO.read(new File("/home/walien/Images/petrus.jpg"));
            bottle.setBase64Image(ImageUtils.encodeToString(img, "jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(DataSourcesBundle.getInstance().getDefaultDataSource().addWineBottleToKnown(bottle));
    }

    @Test
    public void testGetBottleByBarCode() {
        DataSourcesBundle.getInstance().getDefaultDataSource().getBottleByBarCode("3607345750309");
    }

    @Test
    public void testLoadBottles() {
        DataSourcesBundle.getInstance().getDefaultDataSource().loadBottleInCellar(2, 12);
        DataSourcesBundle.getInstance().getDefaultDataSource().loadBottleInCellar(3, 6);
    }

    @Test
    public void testUnloadBottles() {
        DataSourcesBundle.getInstance().getDefaultDataSource().unloadBottleInCellar(2, 6);
        DataSourcesBundle.getInstance().getDefaultDataSource().unloadBottleInCellar(3, 6);
        DataSourcesBundle.getInstance().getDefaultDataSource().unloadBottleInCellar(1, 14);
    }

}
