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
import com.vino.backend.model.Movement;
import com.vino.backend.model.Response;
import com.vino.backend.model.WineCellarRecord;
import com.vino.backend.persistence.Persistor;
import org.joda.time.DateTime;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.Produces;
import restx.annotations.RestxResource;
import restx.factory.Component;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 11:32 PM
 */

@Component
@RestxResource
public class CellarResource extends AbstractResource {

    private Persistor persistor;

    public CellarResource(Persistor persistor) {
        this.persistor = persistor;
    }

    @GET("/cellar")
    public Iterable<WineCellarRecord> getRecords() {
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
    public Response onCellarMovements(Movement movement) {

        if (movement.getRecord() == null) {
            return technical(Response.TechnicalStatus.INVALID_PARAMS);
        }

        // Set the date associated to the movement
        movement.setDate(DateTime.now());

        // Persist movement (history)
        persistor.persist(movement);

        // Persist cellar record
        WineCellarRecord record = movement.getRecord();

        boolean result = false;
        if (movement.getType() == Movement.Type.IN) {
            result = persistor.addInCellar(
                    record.getCode(),
                    record.getDomain(),
                    record.getVintage(),
                    movement.getAmount()
            );
        }

        if (movement.getType() == Movement.Type.OUT) {
            result = persistor.removeFromCellar(
                    record.getKey(),
                    movement.getAmount()
            );
        }
        return business(result);
    }

}
