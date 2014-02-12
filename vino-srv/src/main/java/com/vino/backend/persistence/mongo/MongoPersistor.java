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
import com.vino.backend.logging.Loggers;
import com.vino.backend.model.*;
import com.vino.backend.persistence.Persistor;
import com.vino.backend.reference.Reference;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
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

    @Override
    public WineCellar getCellar() {
        Iterable<WineCellar.Record> records = collections.get(MongoCollections.CELLAR)
                .find()
                .as(WineCellar.Record.class);
        return new WineCellar(ImmutableList.copyOf(records));
    }

    @Override
    public Optional<WineBottle> getBottleByBarCode(Barcode barCode) {
        return Optional.fromNullable(collections.get(MongoCollections.BOTTLES)
                .findOne("{ code.value : #, code.type : # }", barCode.getValue(), barCode.getType())
                .as(WineBottle.class));
    }

    @Override
    public ImmutableList<WineCellar.Record> getRecordsByDomain(String domainKey) {
        Iterable<WineCellar.Record> records = collections.get(MongoCollections.CELLAR)
                .find("{ domain : # }", domainKey)
                .as(WineCellar.Record.class);
        return ImmutableList.copyOf(records);
    }

    @Override
    public Optional<WineCellar.Record> getRecord(String bottleKey) {
        WineCellar.Record record = collections.get(MongoCollections.CELLAR)
                .findOne("{ bottle : # }", bottleKey)
                .as(WineCellar.Record.class);
        return Optional.fromNullable(record);
    }

    ///////////////////////////////////
    // DATA PERSISTENCE
    ///////////////////////////////////

    private void persistEntity(Entity entity, String collection) {

        // Persist entity
        collections.get(collection).save(entity);
        collections.get(MongoCollections.KEYS).save(new EntityKey(entity.getKey(), collection));
        logger.debug("{} '{}' persisted", entity.getClass().getSimpleName(), entity.getKey());
    }

    @Override
    public boolean persist(WineAOC aoc) {
        persistEntity(aoc, MongoCollections.AOCS);
        return true;
    }

    @Override
    public boolean persist(WineRegion region) {
        persistEntity(region, MongoCollections.REGIONS);
        return true;
    }

    @Override
    public boolean persist(WineBottle bottle) {
        persistEntity(bottle, MongoCollections.BOTTLES);
        return true;
    }

    @Override
    public boolean persist(WineDomain domain) {
        persistEntity(domain, MongoCollections.DOMAINS);
        return true;
    }

    @Override
    public boolean addInCellar(Reference<WineBottle> bottle, int quantity) {

        Optional<WineCellar.Record> foundRecord = getRecord(bottle.getKey());

        if (foundRecord.isPresent()) {
            foundRecord.get().setQuantity(foundRecord.get().getQuantity() + quantity);
            collections.get(MongoCollections.CELLAR).save(foundRecord.get());
        } else {
            if (!getEntity(bottle.getKey()).isPresent()) {
                return false;
            }
            collections.get(MongoCollections.CELLAR).save(
                    new WineCellar.Record()
                            .setBottle(bottle)
                            .setQuantity(quantity)
            );
        }

        return true;
    }

    @Override
    public boolean removeFromCellar(Reference<WineBottle> bottle, int quantity) {

        Optional<WineCellar.Record> foundRecord = getRecord(bottle.getKey());

        if (!foundRecord.isPresent()) {
            return false;
        }

        MongoCollection collection = collections.get(MongoCollections.CELLAR);

        if (foundRecord.get().getQuantity() - quantity <= 0) {
            collection.remove(foundRecord.get().getKey());
        } else {
            foundRecord.get().setQuantity(foundRecord.get().getQuantity() - quantity);
            collection.save(foundRecord.get());
        }

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
