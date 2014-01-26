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

package com.vino.mobile.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.github.kevinsawicki.http.HttpRequest;
import com.vino.mobile.model.WineDomain;

import java.io.IOException;
import java.util.List;

/**
 * User: walien
 * Date: 1/26/14
 * Time: 6:35 PM
 */
public class RestClient {

    private static RestClient INSTANCE;

    private final ObjectMapper MAPPER = new ObjectMapper();

    private RestClient() {

        MAPPER.registerModule(new GuavaModule());
        MAPPER.registerModule(new JodaModule());
    }

    public static RestClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestClient();
        }
        return INSTANCE;
    }

    public List<WineDomain> getAllDomains() {

        HttpRequest request = HttpRequest.get("http://192.168.0.10:8080/api/domains");
        if (request.ok()) {
            try {
                return MAPPER.readValue(request.body(), MAPPER.getTypeFactory().constructCollectionType(List.class, WineDomain.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
