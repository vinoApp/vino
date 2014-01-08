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

import com.vino.backend.persistence.Persistor;
import model.WineAOC;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;

import java.util.List;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 11:32 PM
 */

@Component
@RestxResource
public class OriginsResource {

    private Persistor persistor;

    public OriginsResource(Persistor persistor) {
        this.persistor = persistor;
    }

    @GET("/origins/aocs")
    public List<WineAOC> getAllAOCS() {
        return persistor.getAllAOCS();
    }

    @GET("/origins/regions")
    public List<WineAOC> getAllRegions() {
        return persistor.getAllAOCS();
    }

}
