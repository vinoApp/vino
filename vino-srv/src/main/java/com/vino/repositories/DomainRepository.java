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
import com.vino.domain.EntityType;
import com.vino.domain.WineDomain;
import com.vino.persistence.mongo.MongoCollections;
import org.bson.types.ObjectId;
import restx.factory.Component;

@Component
public class DomainRepository {

    private final MongoCollections collections;

    public DomainRepository(MongoCollections collections) {
        this.collections = collections;
    }

    public Iterable<WineDomain> getAllDomains() {
        return collections.from(EntityType.DOMAIN).find().as(WineDomain.class);
    }

    public Optional<WineDomain> getDomainByKey(String key) {
        return Optional.fromNullable(collections.from(EntityType.DOMAIN).findOne(new ObjectId(key)).as(WineDomain.class));
    }

    public Iterable<WineDomain> getDomainsByAOC(String aocKey) {
        return collections.from(EntityType.DOMAIN).find("{ origin: # }", aocKey).as(WineDomain.class);
    }

}
