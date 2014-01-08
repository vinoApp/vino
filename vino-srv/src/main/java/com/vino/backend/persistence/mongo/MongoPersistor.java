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
import model.EntityKey;
import model.WineAOC;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.factory.Component;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 9:46 PM
 */

@Component
public class MongoPersistor implements Persistor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MongoCollection keys;
    private MongoCollection aocs;
    private MongoCollection regions;
    private MongoCollection domains;
    private MongoCollection bottles;

    public MongoPersistor(MongoCollection keys,
                          MongoCollection aocs,
                          MongoCollection regions,
                          MongoCollection domains,
                          MongoCollection bottles) {
        this.keys = keys;
        this.aocs = aocs;
        this.regions = regions;
        this.domains = domains;
        this.bottles = bottles;
    }


    @Override
    public ImmutableList<EntityKey> getAllKeys() {
        return null;
    }

    @Override
    public ImmutableList<WineAOC> getAllAOCS() {
        return ImmutableList.copyOf(this.aocs.find().as(WineAOC.class));
    }
}
