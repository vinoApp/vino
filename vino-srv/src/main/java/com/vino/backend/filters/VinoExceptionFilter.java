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

package com.vino.backend.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.vino.backend.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.*;
import restx.factory.Component;
import restx.http.HttpStatus;
import restx.jackson.FrontObjectMapperFactory;

import javax.inject.Named;
import java.io.IOException;

@Component
public class VinoExceptionFilter implements RestxFilter, RestxHandler {

    private final Logger logger = LoggerFactory.getLogger(VinoExceptionFilter.class);
    private final ObjectMapper mapper;

    public VinoExceptionFilter(@Named(FrontObjectMapperFactory.MAPPER_NAME) ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<RestxHandlerMatch> match(RestxRequest req) {
        return Optional.of(new RestxHandlerMatch(new StdRestxRequestMatch(req.getRestxPath()), this));
    }

    @Override
    public void handle(RestxRequestMatch match, RestxRequest req, RestxResponse resp, RestxContext ctx) throws IOException {

        try {
            ctx.nextHandlerMatch().handle(req, resp, ctx);
        } catch (WebException e) {
            throw e;
        } catch (RuntimeException e) {
            writeTechnicalError(e, resp);
        }
    }

    private void writeTechnicalError(RuntimeException e, RestxResponse resp) throws IOException {

        Response technicalResponse = Response
                .withStatuses(Optional.of(Response.TechnicalStatus.TECHNICAL_ERROR), Optional.<Response.BusinessStatus>absent())
                .withMessage(e.getMessage())
                .build();

        resp
                .setStatus(HttpStatus.BAD_REQUEST)
                .setContentType("application/json")
        ;

        mapper.writeValue(resp.getWriter(), technicalResponse);
    }
}
