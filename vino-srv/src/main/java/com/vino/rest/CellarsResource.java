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

package com.vino.rest;

import com.google.common.base.Optional;
import com.vino.business.CellarBusiness;
import com.vino.domain.WineCellar;
import com.vino.repositories.CellarRepository;
import restx.annotations.*;
import restx.factory.Component;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 11:32 PM
 */

@Component
@RestxResource
public class CellarsResource {

    private final CellarRepository repository;
    private final CellarBusiness business;

    public CellarsResource(CellarRepository repository,
                           CellarBusiness business) {
        this.repository = repository;
        this.business = business;
    }

    @GET("/cellars")
    public Iterable<WineCellar> getCellars() {
        return repository.getAllCellars();
    }

    @GET("/cellars/{key}")
    public Optional<WineCellar> getCellar(String key) {
        return repository.getCellarByKey(key);
    }

    @POST("/cellars")
    @Consumes("application/json")
    public WineCellar createCellar(WineCellar cellar) {
        return business.createCellar(cellar);
    }

    @DELETE("/cellars/{key}")
    public WineCellar removeCellar(String key) {
        return business.removeCellar(key);
    }
}
