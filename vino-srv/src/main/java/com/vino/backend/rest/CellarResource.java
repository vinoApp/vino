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
import com.vino.backend.model.WineBottle;
import com.vino.backend.model.WineCellar;
import com.vino.backend.persistence.Persistor;
import com.vino.backend.reference.Reference;
import com.vino.backend.model.Response;
import restx.annotations.DELETE;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
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
    public WineCellar getCellar() {
        return persistor.getCellar();
    }

    @GET("/cellar/byDomain/{domainKey}")
    public ImmutableList<WineCellar.Record> getRecordsByDomain(String domainKey) {
        return persistor.getRecordsByDomain(domainKey);
    }

    @GET("/cellar/byBottle/{bottleKey}")
    public Optional<WineCellar.Record> getRecord(String bottleKey) {
        return persistor.getRecord(bottleKey);
    }

    @POST("/cellar/{bottleKey}/{qty}")
    public Response addInCelar(String bottleKey, int qty) {

        boolean result = persistor.addInCellar(new Reference<WineBottle>(bottleKey), qty);
        Optional<Response.TechnicalStatus> status = result
                ? Optional.of(Response.TechnicalStatus.OK)
                : Optional.of(Response.TechnicalStatus.DB_ERROR);
        return Response
                .withStatuses(status, Optional.<Response.BusinessStatus>absent())
                .withMessage("Record added in cellar.")
                .build();
    }

    @DELETE("/cellar/{bottleKey}/{qty}")
    public Response removeFromCellar(String bottleKey, int qty) {

        boolean result = persistor.removeFromCellar(new Reference<WineBottle>(bottleKey), qty);
        Optional<Response.TechnicalStatus> status = result
                ? Optional.of(Response.TechnicalStatus.OK)
                : Optional.of(Response.TechnicalStatus.DB_ERROR);
        return Response
                .withStatuses(status, Optional.<Response.BusinessStatus>absent())
                .withMessage("Record updated in cellar.")
                .build();
    }
}
