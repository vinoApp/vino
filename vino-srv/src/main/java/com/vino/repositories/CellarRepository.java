/*
 *
 *  * Copyright 2013 - Elian ORIOU
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.vino.repositories;

import com.google.common.base.Optional;
import com.vino.domain.*;
import com.vino.persistence.mongo.MongoCollections;
import restx.factory.Component;

@Component
public class CellarRepository {

    private final MongoCollections collections;

    public CellarRepository(MongoCollections collections) {

        this.collections = collections;
    }

    public Iterable<WineCellar> getAllCellars() {
        return collections.from(EntityType.CELLAR).find().as(WineCellar.class);
    }

    public Iterable<WineCellarRecord> getAllRecords(Reference<WineCellar> cellar) {
        return collections.from(EntityType.RECORD)
                .find("{ cellar: # }", cellar.getKey())
                .as(WineCellarRecord.class);
    }

    public Iterable<WineCellarRecord> getRecordsByDomain(Reference<WineDomain> domain) {
        return collections.from(EntityType.RECORD)
                .find("{ domain : # }", domain.getKey())
                .as(WineCellarRecord.class);
    }

    public Optional<WineCellarRecord> getRecord(Reference<WineCellar> cellar, Reference<WineDomain> domain, int vintage) {
        return Optional.fromNullable(collections.from(EntityType.RECORD)
                .findOne("{ cellar: #, domain: #, vintage: # }", cellar.getKey(), domain.getKey(), vintage)
                .as(WineCellarRecord.class));
    }

    public Optional<WineCellarRecord> getRecordByBarCode(Reference<WineCellar> cellar, String barcode) {
        return Optional.fromNullable(collections.from(EntityType.RECORD)
                .findOne("{ cellar: #, code.value : # }", cellar.getKey(), barcode)
                .as(WineCellarRecord.class));
    }
}
