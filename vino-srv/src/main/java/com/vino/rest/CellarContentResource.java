/*
 *
 *  * Copyright 2013 - Elian ORIOU
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.vino.rest;

import com.google.common.base.Optional;
import com.vino.business.CellarBusiness;
import com.vino.domain.Movement;
import com.vino.domain.Reference;
import com.vino.domain.WineCellar;
import com.vino.domain.WineCellarRecord;
import com.vino.repositories.CellarRepository;
import restx.annotations.*;
import restx.factory.Component;

@Component
@RestxResource
public class CellarContentResource {

    private final CellarRepository repository;
    private final CellarBusiness business;

    public CellarContentResource(final CellarRepository repository,
                                 final CellarBusiness business) {
        this.repository = repository;
        this.business = business;
    }

    @GET("/cellar/{key}/content")
    @Produces("application/json;view=com.vino.persistence.mongo.Views$Details")
    public Iterable<WineCellarRecord> getRecords(String key) {
        return repository.getAllRecords(Reference.<WineCellar>of(key));
    }

    @GET("/cellar/{key}/content/{barcode}")
    @Produces("application/json;view=com.vino.persistence.mongo.Views$Details")
    public Optional<WineCellarRecord> getRecord(String key, String barcode) {
        return repository.getRecordByBarCode(Reference.<WineCellar>of(key), barcode);
    }

    @POST("/cellar/{key}/content")
    public Movement onCellarMovements(@Param(kind = Param.Kind.PATH) String key, Movement movement) {
        return business.onCellarMovement(key, movement);
    }
}
