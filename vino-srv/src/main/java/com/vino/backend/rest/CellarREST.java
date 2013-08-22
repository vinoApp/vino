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
import com.vino.backend.model.cellar.WineCellarRecord;
import com.vino.backend.model.rest.ResponseStatus;
import com.vino.backend.model.rest.ResponseWrapper;
import com.vino.backend.persistence.DataSourcesBundle;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:39 PM
 */

@Path("/cellar")
public class CellarREST {

    @GET
    @Produces("application/json")
    public List<WineCellarRecord> getAllBottles() {

        List<WineCellarRecord> bottles = DataSourcesBundle.getInstance().getDefaultDataSource().getAllWineBottlesInCellar();
        if (bottles == null) {
            return new ArrayList<WineCellarRecord>();
        }
        return bottles;
    }

    @POST
    @Produces("application/json")
    public ResponseWrapper loadBottles(@FormParam("barcode") String barcode,
                                       @FormParam("qty") int qty) {

        if (barcode == null || qty <= 0) {
            return new ResponseWrapper().setStatus(ResponseStatus.INVALID_PARAMS);
        }

        WineBottle bottle = DataSourcesBundle.getInstance().getDefaultDataSource().getBottleByBarCode(barcode);
        if (bottle == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.BOTTLE_NOT_FOUND);
        }

        boolean status = DataSourcesBundle.getInstance().getDefaultDataSource().loadBottleInCellar(bottle.getId(), qty);
        if (status) {
            return new ResponseWrapper().setStatus(ResponseStatus.OK);
        } else {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_ERROR);
        }
    }

    @DELETE
    @Path("{barcode}")
    @Produces("application/json")
    public ResponseWrapper unloadBottles(@Context UriInfo uriInfo, @PathParam("barcode") String barcode) {


        String qtyParam = uriInfo.getQueryParameters().getFirst("qty");
        if (qtyParam == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.INVALID_PARAMS);
        }
        int qty = Integer.parseInt(qtyParam);

        if (barcode == null || qty <= 0) {
            return new ResponseWrapper().setStatus(ResponseStatus.INVALID_PARAMS);
        }

        WineBottle bottle = DataSourcesBundle.getInstance().getDefaultDataSource().getBottleByBarCode(barcode);
        if (bottle == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.BOTTLE_NOT_FOUND);
        }

        boolean status = DataSourcesBundle.getInstance().getDefaultDataSource().unloadBottleInCellar(bottle.getId(), qty);
        if (status) {
            return new ResponseWrapper().setStatus(ResponseStatus.OK);
        } else {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_ERROR);
        }
    }
}
