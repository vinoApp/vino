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

package com.vino.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;
import com.vino.persistence.Persistor;
import com.vino.serialization.ReferenceDeserializer;
import com.vino.serialization.ReferenceSerializer;

/**
 * User: walien
 * Date: 1/11/14
 * Time: 5:13 PM
 */

@JsonSerialize(using = ReferenceSerializer.class)
@JsonDeserialize(using = ReferenceDeserializer.class)
public class Reference<T extends Entity> {

    private static final ThreadLocal<Persistor> PERSISTOR = new ThreadLocal<>();

    private String key;

    private Optional<T> target;

    public Reference(String key) {
        this.key = key;
    }

    public static <T extends Entity> Reference<T> of(String key) {
        return new Reference<>(key);
    }

    public static <T extends Entity> Reference<T> of(T entity) {
        return new Reference<>(entity.getKey());
    }

    public String getKey() {
        return key;
    }

    public Optional<T> get() {
        if (key == null) {
            return Optional.absent();
        }
        return target = PERSISTOR.get().getEntity(key);
    }

    public static void setPersistor(Persistor persistor) {
        PERSISTOR.set(persistor);
    }

    public static void cleanPersistor() {
        PERSISTOR.remove();
    }

    @Override
    public String toString() {
        return "Reference{" +
                "key='" + key + '\'' +
                ", target=" + target +
                '}';
    }
}
