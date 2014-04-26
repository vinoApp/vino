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
    public Iterable<WineRegion> getAllRegions() {
        logger.debug("Retrieving all regions");
        return collections.get(MongoCollections.REGIONS).find().as(WineRegion.class);
    }

    @Override
    public Iterable<WineAOC> getAllAOCS() {
        logger.debug("Retrieving all aocs");
        return collections.get(MongoCollections.AOCS).find().as(WineAOC.class);
    }

    @Override
    public Iterable<WineDomain> getAllDomains() {
        logger.debug("Retrieving all domains");
        return collections.get(MongoCollections.DOMAINS).find().as(WineDomain.class);
    }

    @Override
    public Iterable<WineDomain> getDomainsByAOC(String aocKey) {
        return collections.get(MongoCollections.DOMAINS).find("{ origin: # }", aocKey).as(WineDomain.class);
    }

    @Override
    public Iterable<WineCellarRecord> getAllRecords() {
        return collections.get(MongoCollections.CELLAR)
                .find()
                .as(WineCellarRecord.class);
    }

    @Override
    public ImmutableList<WineCellarRecord> getRecordsByDomain(String domainKey) {
        Iterable<WineCellarRecord> records = collections.get(MongoCollections.CELLAR)
                .find("{ domain : # }", domainKey)
                .as(WineCellarRecord.class);
        return ImmutableList.copyOf(records);
    }

    @Override
    public Optional<WineCellarRecord> getRecord(String key) {
        return Optional.fromNullable(collections.get(MongoCollections.CELLAR)
                .findOne(new ObjectId(key))
                .as(WineCellarRecord.class));
    }

    @Override
    public Optional<WineCellarRecord> getRecord(Reference<WineDomain> domain, int vintage) {
        return Optional.fromNullable(collections.get(MongoCollections.CELLAR)
                .findOne("{ domain: #, vintage: # }", domain.getKey(), vintage)
                .as(WineCellarRecord.class));
    }

    @Override
    public Optional<WineCellarRecord> getRecordByBarCode(String barcode) {
        return Optional.fromNullable(collections.get(MongoCollections.CELLAR)
                .findOne("{ code.value : # }", barcode)
                .as(WineCellarRecord.class));
    }

    ///////////////////////////////////
    // DATA PERSISTENCE
    ///////////////////////////////////

    private void persistEntity(Entity entity, String collection, boolean addToKeys) {

        // Persist entity
        collections.get(collection).save(entity);
        if (addToKeys) {
            collections.get(MongoCollections.KEYS).save(new EntityKey(entity.getKey(), collection));
        }
        logger.debug("{} '{}' persisted", entity.getClass().getSimpleName(), entity.getKey());
    }

    @Override
    public boolean persist(WineAOC aoc) {
        persistEntity(aoc, MongoCollections.AOCS, true);
        return true;
    }

    @Override
    public boolean persist(WineRegion region) {
        persistEntity(region, MongoCollections.REGIONS, true);
        return true;
    }

    @Override
    public boolean persist(WineDomain domain) {
        persistEntity(domain, MongoCollections.DOMAINS, true);
        return true;
    }

    @Override
    public boolean persist(WineCellarRecord record) {
        persistEntity(record, MongoCollections.CELLAR, true);
        return true;
    }

    @Override
    public boolean persist(Movement movement) {
        persistEntity(movement, MongoCollections.MOVEMENTS, false);
        return true;
    }

    @Override
    public boolean addInCellar(Barcode code, Reference<WineDomain> domain, int vintage, int quantity) {

        Optional<WineCellarRecord> foundRecord = getRecord(domain, vintage);

        if (foundRecord.isPresent()) {
            if (foundRecord.get().getQuantity() + quantity <= 0) {
                delete(foundRecord.get().getKey());
            } else {
                foundRecord.get().setQuantity(foundRecord.get().getQuantity() + quantity);
                collections.get(MongoCollections.CELLAR).save(foundRecord.get());
            }
        } else {
            WineCellarRecord record = new WineCellarRecord()
                    .setCode(code)
                    .setDomain(domain)
                    .setVintage(vintage)
                    .setQuantity(quantity);
            // Aggregate data
            Reference<WineAOC> aoc = record.getDomain().get(this).get().getOrigin();
            Reference<WineRegion> region = aoc.get(this).get().getRegion();
            record.setAoc(aoc);
            record.setRegion(region);
            // Persist entity
            persist(record);
        }

        return true;
    }

    @Override
    public boolean removeFromCellar(String key, int quantity) {

        Optional<WineCellarRecord> record = getEntity(key);

        if (!record.isPresent()) {
            return false;
        }

        MongoCollection collection = collections.get(MongoCollections.CELLAR);

        if (record.get().getQuantity() - quantity <= 0) {
            delete(record.get().getKey());
        } else {
            record.get().setQuantity(record.get().getQuantity() - quantity);
            collection.save(record.get());
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
