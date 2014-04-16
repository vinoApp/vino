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

package com.vino.backend.rest;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.vino.backend.model.Response;
import com.vino.backend.model.WineCellarRecord;
import com.vino.backend.persistence.Persistor;
import restx.annotations.*;
import restx.factory.Component;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 11:32 PM
 */

@Component
@RestxResource
public class CellarResource {

    private Persistor persistor;

    public CellarResource(Persistor persistor) {
        this.persistor = persistor;
    }

    @GET("/cellar")
    public ImmutableList<WineCellarRecord> getRecords() {
        return persistor.getAllRecords();
    }

    @GET("/cellar/barcode")
    public Optional<WineCellarRecord> getRecordByBarCode(String code) {
        return persistor.getRecordByBarCode(code);
    }

    @GET("/cellar/{key}")
    @Produces("application/json;view=com.vino.backend.persistence.mongo.Views$Details")
    public Optional<WineCellarRecord> getRecord(String key) {
        return persistor.getRecord(key);
    }

    @POST("/cellar")
    public Response addInCellar(WineCellarRecord record) {

        boolean result = persistor.addInCellar(
                record.getCode(),
                record.getDomain(),
                record.getVintage(),
                record.getQuantity()
        );
        Optional<Response.TechnicalStatus> status = result
                ? Optional.of(Response.TechnicalStatus.OK)
                : Optional.of(Response.TechnicalStatus.DB_ERROR);
        return Response
                .withStatuses(status, Optional.<Response.BusinessStatus>absent())
                .withMessage("Record added in cellar.")
                .build();
    }

    @PUT("/cellar/{key}/{qty}")
    public Response addInCellar(String key, String qty) {

        boolean result = persistor.addInCellar(key, Integer.parseInt(qty));
        Optional<Response.TechnicalStatus> status = result
                ? Optional.of(Response.TechnicalStatus.OK)
                : Optional.of(Response.TechnicalStatus.DB_ERROR);
        return Response
                .withStatuses(status, Optional.<Response.BusinessStatus>absent())
                .withMessage("Record added in cellar.")
                .build();
    }

    @DELETE("/cellar/{key}/{qty}")
    public Response removeFromCellar(String key, String qty) {

        boolean result = persistor.removeFromCellar(key, Integer.parseInt(qty));
        Optional<Response.TechnicalStatus> status = result
                ? Optional.of(Response.TechnicalStatus.OK)
                : Optional.of(Response.TechnicalStatus.DB_ERROR);
        return Response
                .withStatuses(status, Optional.<Response.BusinessStatus>absent())
                .withMessage("Record updated in cellar.")
                .build();
    }
}
