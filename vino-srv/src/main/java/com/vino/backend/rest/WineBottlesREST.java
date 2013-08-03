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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
        return null;
    }

    @GET
    @Produces("application/json")
    @Path("{barcode}")
    public ResponseWrapper getBottleByBarCode(@PathParam("barcode") String barcode) {
        if (barcode == null || barcode.isEmpty()) {
            return null;
        }
        WineBottle bottle = DataSourcesBundle.getInstance().getDefaultDataSource().getBottleByBarCode(barcode);
        if (bottle == null) {
            return new ResponseWrapper().setStatus(ResponseStatus.BOTTLE_NOT_FOUND);
        }
        return new ResponseWrapper().setBottle(bottle).setStatus(ResponseStatus.OK);
    }

    /*@POST
    @Consumes("application/json")
    public List<WineBottle> addBottle() {

        // Workflow global :
        //
        // L'utilisateur scan un code barre et un appel vers getBottleByBarCode est realise :
        // - Case 1 : La bouteille est trouvee
        //              -> Si l'etiquette de la bouteille n'est pas connue, l'app mobile propose a l'user de la prendre
        //              -> L'utilisateur a la possibilite d'enregister ou de supprimer une bouteille de la cave (appel
        //                 du WS 'cellar').
        //
        // - Case 2 : La bouteille n'est pas trouvee
        //              -> L'utilisateur a la possibilite de prendre une photo de l'etiquette
        //              -> Un appel a cette methode addBottle est realise et la bouteille est place en attente d'edition
        //              -> Apres cela il est invite a rejoindre l'application web pour renseigner les informations
        //                  relatives a la bouteille (+chateau, origine, ...)
        //              -> Apres cela il peut a present ajouter la bouteille a la cave (par l'app web ou par l'appli
        //                  mobile)


        return null;
    }
*/
}
