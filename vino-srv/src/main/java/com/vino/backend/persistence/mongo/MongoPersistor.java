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
import com.vino.backend.logging.Loggers;
import com.vino.backend.model.*;
import com.vino.backend.persistence.Persistor;
import com.vino.backend.reference.Reference;
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
    public Iterable<WineCellar> getAllCellars() {
        return collections.get(MongoCollections.CELLARS).find().as(WineCellar.class);
    }

    @Override
    public Iterable<WineCellarRecord> getAllRecords(Reference<WineCellar> cellar) {
        return collections.get(MongoCollections.RECORDS)
                .find("{ cellar: # }", cellar.getKey())
                .as(WineCellarRecord.class);
    }

    @Override
    public Iterable<WineCellarRecord> getRecordsByDomain(Reference<WineDomain> domain) {
        return collections.get(MongoCollections.RECORDS)
                .find("{ domain : # }", domain.getKey())
                .as(WineCellarRecord.class);
    }

    @Override
    public Optional<WineCellarRecord> getRecord(Reference<WineCellar> cellar, Reference<WineDomain> domain, int vintage) {
        return Optional.fromNullable(collections.get(MongoCollections.RECORDS)
                .findOne("{ cellar: #, domain: #, vintage: # }", cellar.getKey(), domain.getKey(), vintage)
                .as(WineCellarRecord.class));
    }

    @Override
    public Optional<WineCellarRecord> getRecordByBarCode(Reference<WineCellar> cellar, String barcode) {
        return Optional.fromNullable(collections.get(MongoCollections.RECORDS)
                .findOne("{ cellar: #, code.value : # }", cellar.getKey(), barcode)
                .as(WineCellarRecord.class));
    }

    ///////////////////////////////////
    // DATA PERSISTENCE
    ///////////////////////////////////

    private void persistEntity(Entity entity, String collection, boolean addToKeys) {

        // Set entity key
        if (entity.getKey() == null) {
            entity.setKey(new ObjectId().toString());
        }

        // Persist associated key
        if (addToKeys) {
            collections.get(MongoCollections.KEYS).save(new EntityKey(entity.getKey(), collection));
        }

        // Persist entity
        collections.get(collection).save(entity);

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
    public boolean persist(WineCellar cellar) {
        persistEntity(cellar, MongoCollections.CELLARS, true);
        return true;
    }

    @Override
    public boolean persist(WineCellarRecord record) {
        persistEntity(record, MongoCollections.RECORDS, true);
        return true;
    }

    @Override
    public boolean persist(Movement movement) {
        persistEntity(movement, MongoCollections.MOVEMENTS, false);
        return true;
    }

    @Override
    public boolean addInCellar(Reference<WineCellar> cellar, WineCellarRecord record, int quantity) {

        if (cellar == null
                || record.getCode() == null
                || record.getDomain() == null
                || record.getVintage() == 0) {
            throw new IllegalArgumentException("Missing cellar | code | domain | vintage on the provided record");
        }

        Optional<WineCellarRecord> foundRecord = getRecord(cellar, record.getDomain(), record.getVintage());

        if (foundRecord.isPresent()) {
            if (foundRecord.get().getQuantity() + quantity <= 0) {
                delete(foundRecord.get().getKey());
            } else {
                foundRecord.get().setQuantity(foundRecord.get().getQuantity() + quantity);
                persistEntity(foundRecord.get(), MongoCollections.RECORDS, false);
            }
        } else {
            // Aggregate data
            Reference<WineAOC> aoc = record.getDomain().get(this).get().getOrigin();
            Reference<WineRegion> region = aoc.get(this).get().getRegion();
            record.setAoc(aoc);
            record.setRegion(region);
            // Persist entity
            record.setCellar(cellar);
            record.setQuantity(quantity);
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

        if (record.get().getQuantity() - quantity <= 0) {
            delete(record.get().getKey());
        } else {
            record.get().setQuantity(record.get().getQuantity() - quantity);
            persistEntity(record.get(), MongoCollections.RECORDS, false);
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
