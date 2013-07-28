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

package com.vino.backend.persistence.tools;

import com.vino.backend.model.origins.WineAOC;
import com.vino.backend.model.origins.WineDomain;
import com.vino.backend.model.origins.WineRegion;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 8:54 PM
 */
public class MappingUtils {

    public static WineAOC getOriginFromResultSet(ResultSet resultSet) throws SQLException {

        WineRegion region = new WineRegion();
        region.setId(resultSet.getInt(DataRowsBundle.REGION_ID));
        region.setName(resultSet.getString(DataRowsBundle.REGION_NAME));

        WineAOC aoc = new WineAOC();
        aoc.setId(resultSet.getInt(DataRowsBundle.AOC_ID));
        aoc.setName(resultSet.getString(DataRowsBundle.AOC_NAME));
        aoc.setRegion(region);

        return aoc;
    }

    public static WineDomain getWineDomainFromResultSet(ResultSet resultSet) throws SQLException {

        WineDomain domain = new WineDomain();
        domain.setId(resultSet.getInt(DataRowsBundle.DOMAIN_ID));
        domain.setName(resultSet.getString(DataRowsBundle.DOMAIN_NAME));
        domain.setOrigin(getOriginFromResultSet(resultSet));

        return domain;
    }

}
