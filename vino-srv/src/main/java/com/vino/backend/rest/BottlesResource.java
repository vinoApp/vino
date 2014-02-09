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
import com.vino.backend.model.Barcode;
import com.vino.backend.model.Response;
import com.vino.backend.model.WineBottle;
import com.vino.backend.persistence.Persistor;
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
public class BottlesResource {

    private Persistor persistor;

    public BottlesResource(Persistor persistor) {
        this.persistor = persistor;
    }

    @GET("/bottles")
    public ImmutableList<WineBottle> getBottles() {
        return persistor.getAllBottles();
    }

    @GET("/bottles/{key}")
    public Optional<WineBottle> getBottle(String key, boolean isBarCode) {
        if (isBarCode) {
            return persistor.getBottleByBarCode(Barcode.ean13(key));
        } else {
            return persistor.getEntity(key);
        }
    }

    @POST("/bottles")
    public Response addBottle(WineBottle bottle) {

        boolean result = persistor.persist(bottle);
        Optional<Response.TechnicalStatus> status = result
                ? Optional.of(Response.TechnicalStatus.OK)
                : Optional.of(Response.TechnicalStatus.DB_ERROR);
        return Response
                .withStatuses(status, Optional.<Response.BusinessStatus>absent())
                .withMessage("Bottle persisted successfuly !")
                .build();
    }

    @DELETE("/bottles/{key}")
    public Response removeBottle(String key) {

        boolean result = persistor.delete(key);
        Optional<Response.TechnicalStatus> status = result
                ? Optional.of(Response.TechnicalStatus.OK)
                : Optional.of(Response.TechnicalStatus.DB_ERROR);
        return Response
                .withStatuses(status, Optional.<Response.BusinessStatus>absent())
                .withMessage("Bottle removed successfuly !")
                .build();
    }
}
