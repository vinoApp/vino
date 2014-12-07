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

package com.vino.filters;

import com.google.common.base.Optional;
import com.vino.persistence.Persistor;
import com.vino.domain.Reference;
import restx.*;
import restx.factory.Component;

import java.io.IOException;

@Component
public class PersistorFilter implements RestxFilter, RestxHandler {

    private final Persistor persistor;

    public PersistorFilter(Persistor persistor) {
        this.persistor = persistor;
    }

    @Override
    public Optional<RestxHandlerMatch> match(RestxRequest req) {
        return Optional.of(new RestxHandlerMatch(new StdRestxRequestMatch(req.getRestxPath()), this));
    }

    @Override
    public void handle(RestxRequestMatch match, RestxRequest req, RestxResponse resp, RestxContext ctx) throws IOException {

        try {
            Reference.setPersistor(persistor);
            ctx.nextHandlerMatch().handle(req, resp, ctx);
        } finally {
            Reference.cleanPersistor();
        }
    }
}
