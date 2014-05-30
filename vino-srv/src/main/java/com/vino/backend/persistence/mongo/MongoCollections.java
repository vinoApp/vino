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

    public static final String KEYS = "keys";
    public static final String AOCS = "aocs";
    public static final String REGIONS = "regions";
    public static final String DOMAINS = "domains";
    public static final String CELLARS = "cellars";
    public static final String RECORDS = "records";
    public static final String MOVEMENTS = "movements";

    private final JongoCollection keys;
    private final JongoCollection aocs;
    private final JongoCollection regions;
    private final JongoCollection domains;
    private final JongoCollection cellars;
    private final JongoCollection records;
    private final JongoCollection movements;

    public MongoCollections(@Named(KEYS) JongoCollection keys,
                            @Named(AOCS) JongoCollection aocs,
                            @Named(REGIONS) JongoCollection regions,
                            @Named(DOMAINS) JongoCollection domains,
                            @Named(CELLARS) JongoCollection cellar,
                            @Named(RECORDS) JongoCollection records,
                            @Named(MOVEMENTS) JongoCollection movements) {
        this.keys = keys;
        this.aocs = aocs;
        this.regions = regions;
        this.domains = domains;
        this.cellars = cellar;
        this.records = records;
        this.movements = movements;
    }

    public MongoCollection get(String name) {
        switch (name) {
            case KEYS:
                return keys.get();
            case AOCS:
                return aocs.get();
            case REGIONS:
                return regions.get();
            case DOMAINS:
                return domains.get();
            case CELLARS:
                return cellars.get();
            case RECORDS:
                return records.get();
            case MOVEMENTS:
                return movements.get();
        }
        return null;
    }

}
