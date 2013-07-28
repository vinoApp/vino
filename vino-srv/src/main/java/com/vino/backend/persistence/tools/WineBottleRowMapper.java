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

import com.google.common.io.ByteStreams;
import com.vino.backend.log.LoggerBundle;
import com.vino.backend.model.WineBottle;
import com.vino.backend.model.origins.WineAOC;
import com.vino.backend.model.origins.WineDomain;
import com.vino.backend.model.origins.WineRegion;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 7:38 PM
 */
public class WineBottleRowMapper<T> implements RowMapper<T> {

    public WineBottleRowMapper() {
    }

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {

        WineRegion region = new WineRegion();
        region.setId(resultSet.getInt("regionID"));
        region.setName(resultSet.getString("regionName"));

        WineAOC aoc = new WineAOC();
        aoc.setId(resultSet.getInt("aocID"));
        aoc.setName(resultSet.getString("aocName"));
        aoc.setRegion(region);

        WineDomain domain = new WineDomain();
        domain.setId(resultSet.getInt("domainID"));
        domain.setName(resultSet.getString("domainName"));
        domain.setOrigin(aoc);

        WineBottle bottle = new WineBottle();
        bottle.setId(resultSet.getInt("bottleID"));
        bottle.setVintage(resultSet.getInt("vintage"));
        bottle.setBarcode(resultSet.getString("barcode"));
        bottle.setDomain(domain);

        try {
            Blob stickerImage = resultSet.getBlob("stickerImage");
            if (stickerImage != null) {
                bottle.setBase64Image(new String(ByteStreams.toByteArray(
                        stickerImage.getBinaryStream())));
            }
        } catch (IOException e) {
            LoggerBundle.getDefaultLogger().error("Error during base64 to byte[] conversion... : {}", e.getMessage());
        }

        return (T) bottle;
    }
}
