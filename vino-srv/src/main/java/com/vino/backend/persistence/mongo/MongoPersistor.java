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

package com.vino.backend.persistence.mongo;

import com.google.common.collect.ImmutableList;
import com.vino.backend.persistence.Persistor;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.factory.Component;
import restx.jongo.JongoCollection;

import javax.inject.Named;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 9:46 PM
 */

@Component
public class MongoPersistor implements Persistor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private JongoCollection keys;
    private JongoCollection aocs;
    private JongoCollection regions;
    private JongoCollection domains;
    private JongoCollection bottles;

    public MongoPersistor(@Named("keys") JongoCollection keys,
                          @Named("aocs") JongoCollection aocs,
                          @Named("regions") JongoCollection regions,
                          @Named("domains") JongoCollection domains,
                          @Named("bottles") JongoCollection bottles) {
        this.keys = keys;
        this.aocs = aocs;
        this.regions = regions;
        this.domains = domains;
        this.bottles = bottles;
    }

    ///////////////////////////////////
    // DATA ACCESS
    ///////////////////////////////////

    @Override
    public ImmutableList<EntityKey> getAllKeys() {
        logger.debug("Retrieving all keys");
        return ImmutableList.copyOf(this.keys.get().find().as(EntityKey.class));
    }

    @Override
    public ImmutableList<WineRegion> getAllRegions() {
        return ImmutableList.copyOf(this.regions.get().find().as(WineRegion.class));
    }

    @Override
    public ImmutableList<WineAOC> getAllAOCS() {
        return ImmutableList.copyOf(this.aocs.get().find().as(WineAOC.class));
    }

    @Override
    public ImmutableList<WineDomain> getAllDomains() {
        return ImmutableList.copyOf(this.domains.get().find().as(WineDomain.class));
    }

    @Override
    public ImmutableList<WineBottle> getAllBottles() {
        return ImmutableList.copyOf(this.bottles.get().find().as(WineBottle.class));
    }
}
