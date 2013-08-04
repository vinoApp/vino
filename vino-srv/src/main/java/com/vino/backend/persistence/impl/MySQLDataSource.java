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

import com.vino.backend.log.LoggerBundle;
import com.vino.backend.model.WineBottle;
import com.vino.backend.model.origins.WineAOC;
import com.vino.backend.model.origins.WineDomain;
import com.vino.backend.persistence.IDataSource;
import com.vino.backend.persistence.tools.MappingUtils;
import com.vino.backend.persistence.tools.WineAOCRowMapper;
import com.vino.backend.persistence.tools.WineBottleRowMapper;
import com.vino.backend.persistence.tools.WineDomainRowMapper;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:38 PM
 */
public class MySQLDataSource implements IDataSource {

    public static final String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";
    public static final String JDBC_URL = "jdbc:mysql://localhost/vino";
    public static final String DB_USER = "vino";
    public static final String DB_PASS = "vino_pass";

    private JdbcTemplate template;
    private DriverManagerDataSource dataSource;

    public MySQLDataSource() {

        // Init data source
        initDataSource();
        // The JDBC Template for model binding
        template = new JdbcTemplate(dataSource);
    }

    private void initDataSource() {

        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(MYSQL_DRIVER_NAME);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASS);
    }

    ////////////////////
    // RETRIEVING DATA
    ////////////////////

    /**
     * /////////////////////////////////
     * Use at your own risk !
     * /////////////////////////////////
     */

    @Override
    public void resetDB() {

    }

    @Override
    public List<WineBottle> getAllKnownWineBottles() {
        return template.query("SELECT * FROM bottles, domains, regions, aocs " +
                "WHERE bottles.domainID = domains.domainID " +
                "AND domains.aocID = aocs.aocID " +
                "AND aocs.regionID = regions.regionID", new WineBottleRowMapper());
    }

    @Override
    public List<WineBottle> getAllWineBottlesInCellar() {
        return template.query("SELECT * FROM bottles, domains, regions, aocs " +
                "WHERE bottles.domainID = domains.domainID " +
                "AND domains.aocID = aocs.aocID " +
                "AND aocs.regionID = regions.regionID " +
                "AND exists (SELECT * FROM cellar WHERE cellar.bottleID = bottles.bottleID)", new WineBottleRowMapper());
    }

    @Override
    public WineAOC getAOCByID(int id) {
        try {
            return template.queryForObject("SELECT * FROM regions, aocs WHERE aocs.aocID = ? " +
                    "AND aocs.regionID = regions.regionID", new Object[]{id},
                    new WineAOCRowMapper());
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<WineAOC> getAllAOCs() {
        return template.query("SELECT * FROM regions, aocs;", new WineAOCRowMapper());
    }

    @Override
    public WineBottle getBottleByBarCode(String barcode) {
        try {
            return template.queryForObject("SELECT * FROM bottles, domains, regions, aocs " +
                    "WHERE bottles.domainID = domains.domainID " +
                    "AND domains.aocID = aocs.aocID " +
                    "AND aocs.regionID = regions.regionID " +
                    "AND bottles.barcode = ?", new Object[]{barcode}, new WineBottleRowMapper());
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public WineDomain getDomainById(int id) {
        try {
            return template.queryForObject("SELECT * FROM domains, aocs, regions " +
                    "WHERE domains.domainID = ? " +
                    "AND domains.aocID = aocs.aocID " +
                    "AND aocs.regionID = regions.regionID",
                    new Object[]{id},
                    new WineDomainRowMapper());
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<WineDomain> getAllWineDomains() {
        return template.query("SELECT * FROM domains, regions, aocs;", new WineDomainRowMapper());
    }

    //////////////////////////////////////////////////////////////
    // REGISTERING/UNREGISTERING BOTTLES INTO KNOWN BOTTLES SET
    //////////////////////////////////////////////////////////////

    @Override
    public boolean addWineBottleToKnown(WineBottle bottle) {
        if (!MappingUtils.validateWineBottleObject(bottle)) {
            return false;
        }
        return template.update("INSERT INTO bottles (barcode, domainID, vintage, isValidated) VALUES (?, ?, ?, ?, ?)",
                bottle.getBarcode(),
                bottle.getDomain().getId(),
                bottle.getVintage(),
                bottle.getIsValidated()) != 0;
    }

    @Override
    public boolean updateKnownWineBottle(int bottleID, WineBottle newBottle) {

        int domainID = 0;
        if (newBottle.getDomain() != null) {
            domainID = newBottle.getDomain().getId();
        }

        return template.update("UPDATE bottles b " +
                "SET b.vintage = CASE WHEN ? IS null THEN b.vintage ELSE ? END," +
                "b.domainID = CASE WHEN ? = 0 THEN b.domainID ELSE ? END," +
                "b.isValidated = ? " +
                "WHERE b.bottleID = ?",
                newBottle.getVintage(), newBottle.getVintage(), domainID, domainID,
                newBottle.getIsValidated(), bottleID) != 0;
    }

    @Override
    public boolean removeWineBottleFromKnown(int id) {
        if (id == 0) {
            return false;
        }
        return template.update("DELETE FROM bottles WHERE bottleID = ?", id) != 0;
    }


    //////////////////////////////////////////////////////////////
    // REGISTERING/UNREGISTERING DOMAINS
    //////////////////////////////////////////////////////////////

    @Override
    public boolean addWineDomain(WineDomain domain) {
        if (!MappingUtils.validateWineDomainObject(domain)) {
            return false;
        }
        return template.update("INSERT INTO domains (domainName, aocID, stickerImage) VALUES (?, ?, ?)", domain.getName(),
                domain.getOrigin().getId(), domain.getSticker()) != 0;
    }

    @Override
    public boolean removeWineDomain(int id) {
        if (id == 0) {
            return false;
        }
        return template.update("DELETE FROM domains WHERE domainID = ?", id) != 0;
    }

    //////////////////////////////////////////////////
    // REGISTERING/UNREGISTERING BOTTLES INTO CELLAR
    //////////////////////////////////////////////////

    @Override
    public boolean loadBottleInCellar(int id, int qty) {
        if (id == 0) {
            return false;
        }
        try {
            int currentQty = template.queryForObject("SELECT qty FROM cellar WHERE bottleID = ?;", new Object[]{id}, Integer.class);
            if (currentQty <= 0) {
                LoggerBundle.getDefaultLogger().error("The loaded quantity of the bottle '{}' is negative !", id);
            }
            return template.update("UPDATE cellar SET qty = ? WHERE bottleID = ?", currentQty + qty, id) != 0;
        } catch (IncorrectResultSizeDataAccessException e) {
            LoggerBundle.getDefaultLogger().info("The bottle '{}' was not loaded into the cellar, now it is.", id);
            return template.update("INSERT INTO cellar (bottleID, qty) VALUES (?, ?);", id, qty) != 0;
        }
    }

    @Override
    public boolean unloadBottleInCellar(int id, int qty) {
        if (id == 0) {
            return false;
        }
        try {
            int currentQty = template.queryForObject("SELECT qty FROM cellar WHERE bottleID = ?;", new Object[]{id}, Integer.class);
            if (currentQty - qty <= 0) {
                LoggerBundle.getDefaultLogger().info("There is no bottle '{}' anymore (the qty is down to 0)", id);
                return template.update("DELETE FROM cellar WHERE bottleID = ?", id) != 0;
            } else {
                LoggerBundle.getDefaultLogger().info("The new qty of bottle '{}' in the cellar is : {}", id, qty);
                return template.update("UPDATE cellar SET qty = ? WHERE bottleID = ?", currentQty - qty, id) != 0;
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            LoggerBundle.getDefaultLogger().error("The bottle '{}' is not loaded into the cellar !", id);
            return false;
        }
    }
}


