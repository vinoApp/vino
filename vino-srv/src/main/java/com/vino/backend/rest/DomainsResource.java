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
import com.vino.backend.model.WineDomain;
import com.vino.backend.persistence.Persistor;
import restx.annotations.*;
import restx.factory.Component;
import restx.security.PermitAll;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 11:32 PM
 */

@Component
@RestxResource
public class DomainsResource {

    private Persistor persistor;

    public DomainsResource(Persistor persistor) {
        this.persistor = persistor;
    }

    @GET("/domains")
    @PermitAll
    public ImmutableList<WineDomain> getAllDomains(Optional<String> aoc) {
        if (aoc.isPresent()) {
            return persistor.getDomainsByAOC(aoc.get());
        }
        return persistor.getAllDomains();
    }

    @GET("/domains/{key}")
    @Produces("application/json;view=com.vino.backend.persistence.mongo.Views$Details")
    public Optional<WineDomain> getDomain(String key) {
        return persistor.getEntity(key);
    }

    @GET("/domains/{key}/records")
    public ImmutableList<WineCellarRecord> getRecordsLinkedToDomain(String key) {
        return persistor.getRecordsByDomain(key);
    }

    @POST("/domains")
    public Response addDomain(WineDomain domain) {
        boolean result = persistor.persist(domain);
        Response.TechnicalStatus technical = result ? Response.TechnicalStatus.DB_INSERT_OK : Response.TechnicalStatus.DB_ERROR;
        return Response
                .withStatuses(Optional.of(technical), Optional.<Response.BusinessStatus>absent())
                .withMessage("The domain '%s' was properly persisted.", domain.getName())
                .build();
    }

    @DELETE("/domains/{key}")
    public Response deleteDomain(String key) {
        boolean result = persistor.delete(key);
        Response.TechnicalStatus technical = result ? Response.TechnicalStatus.DB_REMOVE_OK : Response.TechnicalStatus.DB_ERROR;
        return Response
                .withStatuses(Optional.of(technical), Optional.<Response.BusinessStatus>absent())
                .withMessage("The domain '%s' was properly deleted.", key)
                .build();
    }
}
