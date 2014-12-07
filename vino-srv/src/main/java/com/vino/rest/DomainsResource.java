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
import com.vino.business.DomainBusiness;
import com.vino.domain.Reference;
import com.vino.domain.WineCellarRecord;
import com.vino.domain.WineDomain;
import com.vino.repositories.CellarRepository;
import com.vino.repositories.DomainRepository;
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

    private final DomainRepository domainRepository;
    private final CellarRepository cellarRepository;
    private final DomainBusiness business;

    public DomainsResource(DomainRepository domainRepository,
                           CellarRepository cellarRepository,
                           DomainBusiness business) {
        this.domainRepository = domainRepository;
        this.cellarRepository = cellarRepository;
        this.business = business;
    }

    @GET("/domains")
    @PermitAll
    public Iterable<WineDomain> getAllDomains(Optional<String> aoc) {
        if (aoc.isPresent()) {
            return domainRepository.getDomainsByAOC(aoc.get());
        }
        return domainRepository.getAllDomains();
    }

    @GET("/domains/{key}")
    @Produces("application/json;view=com.vino.persistence.mongo.Views$Details")
    public Optional<WineDomain> getDomain(String key) {
        return domainRepository.getDomainByKey(key);
    }

    @GET("/domains/{key}/records")
    public Iterable<WineCellarRecord> getRecordsLinkedToDomain(String key) {
        return cellarRepository.getRecordsByDomain(Reference.<WineDomain>of(key));
    }

    @POST("/domains")
    @Consumes("application/json;view=com.vino.persistence.mongo.Views$Edit")
    public WineDomain addOrUpdateDomain(WineDomain domain) {
        return business.createDomain(domain);
    }

    @DELETE("/domains/{key}")
    public WineDomain deleteDomain(String key) {
        return business.deleteDomain(key);
    }
}
