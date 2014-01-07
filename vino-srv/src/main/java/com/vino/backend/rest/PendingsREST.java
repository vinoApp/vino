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

import com.vino.backend.model.PendingWineBottle;
import com.vino.backend.model.rest.ResponseStatus;
import com.vino.backend.model.rest.ResponseWrapper;
import com.vino.backend.persistence.PendingsBundle;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:35 PM
 */

@Path("/pendings")
public class PendingsREST {

    @GET
    @Produces("application/json")
    public List<PendingWineBottle> getAllPendings() {
        return new ArrayList<PendingWineBottle>(PendingsBundle.getInstance().getPendings().values());
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseWrapper addPendingBottle(PendingWineBottle pending) {

        // Check the provided object
        if (pending == null || pending.getBarcode() == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.INVALID_PARAMS);
        }

        // Set the adding date
        pending.setAddingDate(new Date());

        // Add the pending wine bottle to the in-memory data bundle
        PendingsBundle.getInstance().getPendings().put(pending.getBarcode(), pending);

        return new ResponseWrapper().setStatus(ResponseStatus.OK);
    }

    @DELETE
    @Path("{barcode}")
    @Produces("application/json")
    public ResponseWrapper removePendingBottle(@PathParam("barcode") String barcode) {

        // Check the provided object
        if (barcode == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.INVALID_PARAMS);
        }

        PendingsBundle.getInstance().getPendings().remove(barcode);

        return new ResponseWrapper().setStatus(ResponseStatus.OK);
    }


}
