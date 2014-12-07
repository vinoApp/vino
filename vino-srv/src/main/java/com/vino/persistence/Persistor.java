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

package com.vino.persistence;

import com.google.common.base.Optional;
import com.vino.domain.Entity;
import com.vino.domain.EntityKey;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:37 PM
 */
public interface Persistor {

    Optional<EntityKey> getEntityKey(String key);

    <T extends Entity> Optional<T> getEntity(String key);

    <T extends Entity> T persist(T entity);

    <T extends Entity> T persist(T entity, boolean addToKeys);

    boolean delete(String key);
}
