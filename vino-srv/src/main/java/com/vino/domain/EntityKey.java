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

package com.vino.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 9:41 PM
 */
public class EntityKey {

    @Id
    @ObjectId
    private String key;

    private EntityType type;

    @JsonCreator
    public EntityKey(@JsonProperty("_id") String key,
                     @JsonProperty("type") String type) {
        this.key = key;
        this.type = EntityType.valueOf(type);
    }

    public static EntityKey of(Entity entity) {
        return new EntityKey(entity.getKey(), entity.getType().name());
    }

    public String getKey() {
        return key;
    }

    public EntityType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "EntityKey{" +
                "key='" + key + '\'' +
                ", type=" + type +
                '}';
    }
}
