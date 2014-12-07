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

package com.vino.persistence.mongo;

import com.vino.domain.EntityType;
import org.jongo.MongoCollection;
import restx.factory.Component;
import restx.jongo.JongoCollection;

import javax.inject.Named;

/**
 * User: walien
 * Date: 1/8/14
 * Time: 12:51 AM
 */

@Component
public class MongoCollections {

    private static final String AOCS = "aocs";
    private static final String REGIONS = "regions";
    private static final String DOMAINS = "domains";
    private static final String CELLARS = "cellars";
    private static final String RECORDS = "records";
    private static final String MOVEMENTS = "movements";

    private final JongoCollection aocs;
    private final JongoCollection regions;
    private final JongoCollection domains;
    private final JongoCollection cellars;
    private final JongoCollection records;
    private final JongoCollection movements;

    public MongoCollections(@Named(AOCS) JongoCollection aocs,
                            @Named(REGIONS) JongoCollection regions,
                            @Named(DOMAINS) JongoCollection domains,
                            @Named(CELLARS) JongoCollection cellar,
                            @Named(RECORDS) JongoCollection records,
                            @Named(MOVEMENTS) JongoCollection movements) {
        this.aocs = aocs;
        this.regions = regions;
        this.domains = domains;
        this.cellars = cellar;
        this.records = records;
        this.movements = movements;
    }

    public MongoCollection from(EntityType type) {
        switch (type) {
            case AOC:
                return aocs.get();
            case REGION:
                return regions.get();
            case DOMAIN:
                return domains.get();
            case CELLAR:
                return cellars.get();
            case RECORD:
                return records.get();
            case MOVEMENT:
                return movements.get();
        }
        return null;
    }
}
