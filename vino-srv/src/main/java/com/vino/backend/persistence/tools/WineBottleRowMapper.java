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

    public static final String REGION_ID = "regionID";
    public static final String REGION_NAME = "regionName";
    public static final String AOC_ID = "aocID";
    public static final String AOC_NAME = "aocName";
    public static final String DOMAIN_ID = "domainID";
    public static final String DOMAIN_NAME = "domainName";
    public static final String BOTTLE_ID = "bottleID";
    public static final String VINTAGE = "vintage";
    public static final String BARCODE = "barcode";
    public static final String STICKER_IMAGE = "stickerImage";

    public WineBottleRowMapper() {
    }

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {

        WineRegion region = new WineRegion();
        region.setId(resultSet.getInt(REGION_ID));
        region.setName(resultSet.getString(REGION_NAME));

        WineAOC aoc = new WineAOC();
        aoc.setId(resultSet.getInt(AOC_ID));
        aoc.setName(resultSet.getString(AOC_NAME));
        aoc.setRegion(region);

        WineDomain domain = new WineDomain();
        domain.setId(resultSet.getInt(DOMAIN_ID));
        domain.setName(resultSet.getString(DOMAIN_NAME));
        domain.setOrigin(aoc);

        WineBottle bottle = new WineBottle();
        bottle.setId(resultSet.getInt(BOTTLE_ID));
        bottle.setVintage(resultSet.getInt(VINTAGE));
        bottle.setBarcode(resultSet.getString(BARCODE));
        bottle.setDomain(domain);

        try {
            Blob stickerImage = resultSet.getBlob(STICKER_IMAGE);
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
