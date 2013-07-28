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
import com.vino.backend.persistence.tools.WineBottleRowMapper;
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

    @Override
    public List<WineBottle> getAllKnownWineBottles() {
        return template.query("SELECT * FROM bottles, aocs, regions, domains " +
                "WHERE bottles.domainID = domains.domainID " +
                "AND domains.aocID = aocs.aocID " +
                "AND aocs.regionID = regions.regionID", new WineBottleRowMapper<WineBottle>());
    }

    @Override
    public List<WineBottle> getAllWineBottlesInCellar() {
        return template.queryForList("SELECT * FROM bottles b WHERE exists (SELECT * FROM cellar c WHERE c.idBottle = b.idBottle);", WineBottle.class);
    }

    @Override
    public List<WineAOC> getAllAOCs() {
        return template.queryForList("SELECT * FROM aocs;", WineAOC.class);
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


