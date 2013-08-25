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
import com.vino.backend.model.origins.WineDomain;
import com.vino.backend.model.rest.ResponseStatus;
import com.vino.backend.model.rest.ResponseWrapper;
import com.vino.backend.persistence.DataSourcesBundle;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:39 PM
 */

@Path("/domains")
public class DomainsREST {

    @GET
    @Produces("application/json")
    public List<WineDomain> getAllDomains() {

        List<WineDomain> wineDomains = DataSourcesBundle.getInstance().getDefaultDataSource().getAllWineDomains();
        if (wineDomains == null) {
            return new ArrayList<WineDomain>();
        }
        return wineDomains;
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public ResponseWrapper getDomain(@PathParam("id") int id) {

        WineDomain wineDomain = DataSourcesBundle.getInstance().getDefaultDataSource().getDomainById(id);
        if (wineDomain == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.DOMAIN_NOT_FOUND);
        }
        return new ResponseWrapper().setData(wineDomain).setStatus(ResponseStatus.OK);
    }


    @POST
    @Produces("application/json")
    public ResponseWrapper createDomain(WineDomain domain) {

        boolean result = DataSourcesBundle.getInstance().getDefaultDataSource().addWineDomain(domain);
        if (!result) {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_ERROR);
        }
        return new ResponseWrapper().setStatus(ResponseStatus.OK);
    }

    @POST
    @Path("{id}")
    @Produces("application/json")
    public ResponseWrapper saveDomain(@PathParam("id") int id, WineDomain domain) {

        boolean result = DataSourcesBundle.getInstance().getDefaultDataSource().updateWineDomain(id, domain);
        if (!result) {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_ERROR);
        }
        return new ResponseWrapper().setStatus(ResponseStatus.OK);
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public ResponseWrapper removeDomain(@PathParam("id") int id) {

        boolean result = DataSourcesBundle.getInstance().getDefaultDataSource().removeWineDomain(id);
        if (!result) {
            return new ResponseWrapper().setStatus(ResponseStatus.DB_ERROR);
        }
        return new ResponseWrapper().setStatus(ResponseStatus.OK);
    }

    @GET
    @Path("{id}/bottles")
    @Produces("application/json")
    public List<WineBottle> getBottlesByDomain(@PathParam("id") int id) {

        List<WineBottle> wineBottles = DataSourcesBundle.getInstance().getDefaultDataSource().getBottlesByDomain(id);
        if (wineBottles == null) {
            return new ArrayList<WineBottle>();
        }
        return wineBottles;
    }

}
