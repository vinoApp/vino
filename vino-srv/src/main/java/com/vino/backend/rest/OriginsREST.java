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

import com.vino.backend.model.origins.WineAOC;
import com.vino.backend.persistence.DataSourcesBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:39 PM
 */

@Path("/origins")
public class OriginsREST {

    @GET
    @Produces("application/json")
    public List<WineAOC> getAllOrigins() {

        List<WineAOC> allOrigins = DataSourcesBundle.getInstance().getDefaultDataSource().getAllOrigins();
        if (allOrigins == null) {
            return new ArrayList<WineAOC>();
        }
        return allOrigins;
    }
}
