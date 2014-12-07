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

package com.vino.business;

import com.vino.domain.WineDomain;
import com.vino.persistence.Persistor;
import com.vino.repositories.CellarRepository;
import restx.factory.Component;

@Component
public class DomainBusiness {

    private final Persistor persistor;
    private final CellarRepository repository;

    public DomainBusiness(Persistor persistor,
                          CellarRepository repository) {
        this.persistor = persistor;
        this.repository = repository;
    }

    public WineDomain createDomain(WineDomain domain) {
        return persistor.persist(domain);
    }

    public WineDomain deleteDomain(String key) {
        persistor.delete(key);
        return (WineDomain) new WineDomain().setKey(key);
    }

}
