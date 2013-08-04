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

import com.vino.backend.model.WineBottle;
import com.vino.backend.model.origins.WineDomain;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 7:38 PM
 */
public class WineBottleRowMapper implements RowMapper<WineBottle> {

    @Override
    public WineBottle mapRow(ResultSet resultSet, int i) throws SQLException {

        WineDomain domain = MappingUtils.getWineDomainFromResultSet(resultSet);

        WineBottle bottle = new WineBottle();
        bottle.setId(resultSet.getInt(DataRowsBundle.BOTTLE_ID));
        bottle.setVintage(resultSet.getInt(DataRowsBundle.VINTAGE));
        bottle.setBarcode(resultSet.getString(DataRowsBundle.BARCODE));
        bottle.setValidated(resultSet.getBoolean(DataRowsBundle.IS_VALIDATED));
        bottle.setDomain(domain);

        return bottle;
    }


}
