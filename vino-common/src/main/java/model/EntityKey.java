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

package model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * User: walien
 * Date: 1/7/14
 * Time: 9:41 PM
 */
public class EntityKey extends Entity {

    private String key;

    private String collection;

    @JsonCreator
    public EntityKey(@JsonProperty("_id") String key,
                     @JsonProperty("collection") String collection) {
        this.key = key;
        this.collection = collection;
    }

    public String getKey() {
        return key;
    }

    public String getCollection() {
        return collection;
    }

    @Override
    public String toString() {
        return "EntityKey{" +
                "key='" + key + '\'' +
                ", collection='" + collection + '\'' +
                '}';
    }
}
