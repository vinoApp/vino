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

import com.google.common.base.Optional;
import com.vino.domain.Entity;
import com.vino.domain.EntityKey;
import com.vino.persistence.Persistor;
import org.bson.types.ObjectId;
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

    private static final Logger logger = LoggerFactory.getLogger(MongoPersistor.class);
    private final JongoCollection keys;
    private final MongoCollections collections;

    public MongoPersistor(@Named("keys") JongoCollection keys,
                          MongoCollections collections) {
        this.keys = keys;
        this.collections = collections;
    }

    ///////////////////////////////////
    // DATA ACCESS
    ///////////////////////////////////

    @Override
    public Optional<EntityKey> getEntityKey(String key) {
        return Optional.fromNullable(keys.get().findOne(new ObjectId(key)).as(EntityKey.class));
    }

    @Override
    public <T extends Entity> Optional<T> getEntity(String key) {
        Optional<EntityKey> entityKey = getEntityKey(key);
        if (!entityKey.isPresent()) {
            return Optional.absent();
        }
        return Optional.fromNullable(
                (T) collections.from(entityKey.get().getType()).findOne(new ObjectId(entityKey.get().getKey())).as(Entity.class)
        );
    }

    ///////////////////////////////////
    // DATA PERSISTENCE
    ///////////////////////////////////

    @Override
    public <T extends Entity> T persist(T entity) {
        return persist(entity, true);
    }

    @Override
    public <T extends Entity> T persist(T entity, boolean addToKeys) {

        // Set entity key
        if (entity.getKey() == null) {
            entity.setKey(new ObjectId().toString());
        }

        // Persist associated key
        if (addToKeys) {
            keys.get().save(new EntityKey(entity.getKey(), entity.getType().name()));
        }

        // Persist entity
        collections.from(entity.getType()).save(entity);

        logger.debug("{} '{}' persisted", entity.getClass().getSimpleName(), entity.getKey());

        return entity;
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
        keys.get().remove(new ObjectId(key));
        // Remove from entity collection
        collections.from(entityKey.get().getType()).remove(new ObjectId(key));
        logger.debug("Entity of type '{}' was deleted :'{}'", entityKey.get().getType().name(), key);

        return true;
    }
}
