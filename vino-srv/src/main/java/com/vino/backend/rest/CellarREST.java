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

package com.vino.backend.rest;

import com.vino.backend.model.WineBottle;
import com.vino.backend.model.rest.ResponseWrapper;

import javax.ws.rs.*;
import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:39 PM
 */

@Path("/cellar")
public class CellarREST {

    @GET
    @Consumes("application/json")
    public List<WineBottle> getAllBottles() {
        return null;
    }

    @POST
    @Consumes("application/json")
    public ResponseWrapper registerBottles(String barcode, int qty) {
        return null;
    }

    @DELETE
    @Consumes("application/json")
    public ResponseWrapper unregisterBottles(String barcode, int qty) {
        return null;
    }
}
