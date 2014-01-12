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

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.vino.backend.model.*;
import com.vino.backend.persistence.Persistor;
import logging.Loggers;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import restx.factory.Component;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 9:46 PM
 */

@Component
public class MongoPersistor implements Persistor {

    private Logger logger = Loggers.SRV;
    private MongoCollections collections;

    public MongoPersistor(MongoCollections collections) {
        this.collections = collections;
    }

    ///////////////////////////////////
    // DATA ACCESS
    ///////////////////////////////////

    @Override
    public Optional<EntityKey> getEntityKey(String key) {
        return Optional.fromNullable(collections.get(MongoCollections.KEYS).findOne(new ObjectId(key)).as(EntityKey.class));
    }

    @Override
    public <T extends Entity> Optional<T> getEntity(String key) {
        Optional<EntityKey> entityKey = getEntityKey(key);
        if (!entityKey.isPresent()) {
            logger.warn("Entity not found '{}'", key);
            return Optional.absent();
        }
        logger.debug("Retrieving entity by key : {} from {}", entityKey.get().getKey(), entityKey.get().getCollection());
        return Optional.fromNullable(
                (T) collections.get(entityKey.get().getCollection()).findOne(new ObjectId(entityKey.get().getKey())).as(Entity.class)
        );
    }

    @Override
    public ImmutableList<WineRegion> getAllRegions() {
        logger.debug("Retrieving all regions");
        return ImmutableList.copyOf(collections.get(MongoCollections.REGIONS).find().as(WineRegion.class));
    }

    @Override
    public ImmutableList<WineAOC> getAllAOCS() {
        logger.debug("Retrieving all aocs");
        return ImmutableList.copyOf(collections.get(MongoCollections.AOCS).find().as(WineAOC.class));
    }

    @Override
    public ImmutableList<WineDomain> getAllDomains() {
        logger.debug("Retrieving all domains");
        return ImmutableList.copyOf(collections.get(MongoCollections.DOMAINS).find().as(WineDomain.class));
    }

    @Override
    public ImmutableList<WineBottle> getAllBottles() {
        logger.debug("Retrieving all bottles");
        return ImmutableList.copyOf(collections.get(MongoCollections.BOTTLES).find().as(WineBottle.class));
    }

    ///////////////////////////////////
    // DATA PERSISTENCE
    ///////////////////////////////////

    @Override
    public boolean persist(WineAOC aoc) {
        // Check if the entity already exists
        if (collections.get(MongoCollections.AOCS)
                .count(" { name : #, region : # } ", aoc.getName(), aoc.getRegion().getKey()) > 0) {
            logger.warn("AOC '{}' already exists", aoc.getKey());
            return false;
        }
        // Persist
        collections.get(MongoCollections.AOCS).save(aoc);
        collections.get(MongoCollections.KEYS).save(new EntityKey(aoc.getKey(), MongoCollections.AOCS));
        logger.debug("AOC '{}' persisted", aoc.getKey());
        return true;
    }

    @Override
    public boolean persist(WineRegion region) {
        // Check if the entity already exists
        if (collections.get(MongoCollections.REGIONS)
                .count(" { name : # } ", region.getName()) > 0) {
            logger.warn("Region '{}' already exists", region.getName());
            return false;
        }
        // Persist
        collections.get(MongoCollections.REGIONS).save(region);
        collections.get(MongoCollections.KEYS).save(new EntityKey(region.getKey(), MongoCollections.REGIONS));
        logger.debug("Region '{}' persisted", region.getKey());
        return true;
    }

    @Override
    public boolean persist(WineDomain domain) {
        // Check if the entity already exists
        if (collections.get(MongoCollections.DOMAINS)
                .count(" { name : #, origin : # } ", domain.getName(), domain.getOrigin().getKey()) > 0) {
            logger.warn("Domain '{}' already exists", domain.getName());
            return false;
        }
        // Persist
        collections.get(MongoCollections.DOMAINS).save(domain);
        collections.get(MongoCollections.KEYS).save(new EntityKey(domain.getKey(), MongoCollections.DOMAINS));
        logger.debug("Domain '{}' persisted", domain.getKey());
        return true;
    }

    ///////////////////////////////////
    // DATA DELETION
    ///////////////////////////////////

    @Override
    public boolean delete(String key) {
        Optional<EntityKey> entityKey = getEntityKey(key);
        if (!entityKey.isPresent()) {
            logger.warn("Entity not found '{}'", key);
            return false;
        }
        // Remove from keys
        collections.get(MongoCollections.KEYS).remove(new ObjectId(entityKey.get().getKey()));
        // Remove from entity collection
        collections.get(entityKey.get().getCollection()).remove(new ObjectId(entityKey.get().getKey()));
        logger.debug("Entity '{}' is deleted from '{}'", key, entityKey.get().getCollection());
        return true;
    }
}
