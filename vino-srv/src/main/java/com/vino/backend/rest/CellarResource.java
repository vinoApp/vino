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

import com.vino.backend.model.Response;
import com.vino.backend.model.WineCellar;
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
public class CellarResource extends AbstractResource {

    private final Persistor persistor;

    public CellarResource(Persistor persistor) {
        this.persistor = persistor;
    }

    @GET("/cellar")
    public Iterable<WineCellar> getCellars() {
        return persistor.getAllCellars();
    }

    @POST("/cellar")
    @Consumes("application/json")
    public Response addCellar(WineCellar cellar) {
        return business(persistor.persist(cellar));
    }

    @DELETE("/cellar/{cellarKey}")
    public Response removeCellar(String cellarKey) {
        return business(persistor.delete(cellarKey));
    }
}
