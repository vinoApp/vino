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
import com.vino.backend.model.rest.ResponseStatus;
import com.vino.backend.model.rest.ResponseWrapper;
import com.vino.backend.persistence.DataSourcesBundle;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:35 PM
 */

@Path("/bottles")
public class WineBottlesREST {

    @GET
    @Produces("application/json")
    public List<WineBottle> getAllBottles() {
        List<WineBottle> bottles = DataSourcesBundle.getInstance().getDefaultDataSource().getAllKnownWineBottles();
        if (bottles == null) {
            return new ArrayList<WineBottle>();
        }
        return bottles;
    }

    @GET
    @Produces("application/json")
    @Path("/id/{id}")
    public ResponseWrapper getBottle(@PathParam("id") int id) {
        if (id <= 0) {
            return new ResponseWrapper().setStatus(ResponseStatus.INVALID_PARAMS);
        }
        WineBottle bottle = DataSourcesBundle.getInstance().getDefaultDataSource().getBottleById(id);
        if (bottle == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.BOTTLE_NOT_FOUND);
        }
        return new ResponseWrapper().setData(bottle).setStatus(ResponseStatus.OK);
    }

    @GET
    @Produces("application/json")
    @Path("/barcode/{barcode}")
    public ResponseWrapper getBottleByBarCode(@PathParam("barcode") String barcode) {
        if (barcode == null || barcode.isEmpty()) {
            return new ResponseWrapper().setStatus(ResponseStatus.INVALID_PARAMS);
        }
        WineBottle bottle = DataSourcesBundle.getInstance().getDefaultDataSource().getBottleByBarCode(barcode);
        if (bottle == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.BOTTLE_NOT_FOUND);
        }
        return new ResponseWrapper().setData(bottle).setStatus(ResponseStatus.OK);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseWrapper addBottle(WineBottle bottle) {

        // Add the bottle to the DB
        boolean status = DataSourcesBundle.getInstance().getDefaultDataSource().addWineBottleToKnown(bottle);
        if (status) {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_INSERT_OK);
        } else {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_ERROR);
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{bottle-id}")
    public ResponseWrapper updateBottle(@PathParam("bottle-id") int bottleID,
                                        WineBottle bottle) {
        // Update the bottle
        boolean status = DataSourcesBundle.getInstance().getDefaultDataSource().updateKnownWineBottle(bottleID, bottle);
        if (status) {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_INSERT_OK);
        } else {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_ERROR);
        }
    }
}
