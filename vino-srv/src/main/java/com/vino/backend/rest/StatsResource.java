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

import com.google.common.base.Optional;
import com.vino.backend.model.stats.CellarStatRecord;
import com.vino.backend.model.stats.MovementStatRecord;
import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.jongo.JongoCollection;

import javax.inject.Named;

@Component
@RestxResource
public class StatsResource {

    private final JongoCollection movements;
    private final JongoCollection cellar;

    public StatsResource(@Named("movements") JongoCollection movements,
                         @Named("cellar") JongoCollection cellar) {
        this.movements = movements;
        this.cellar = cellar;
    }

    @GET("/stats/mostConsumedDomain")
    public Iterable<MovementStatRecord> getMostConsumedWineDomain(Optional<String> limit) {

        if (!limit.isPresent()) {
            limit = Optional.of("5");
        }

        return movements.get()
                .aggregate("{ $match : { type : # }}", "OUT")
                .and("{ $group : { _id: '$record.domain', count: { $sum : 1 } } }")
                .and("{ $project : { domain: '$_id', count: 1 } }")
                .as(MovementStatRecord.class);
    }

    @GET("/stats/cellarStockByVintage")
    public Iterable<CellarStatRecord> getCellarStockByVintage(Optional<String> limit) {

        if (!limit.isPresent()) {
            limit = Optional.of("5");
        }

        return cellar.get()
                .aggregate("{ $group : { _id: '$vintage', count: { $sum : 1 } } }")
                .and("{ $project : { vintage: '$_id', count: 1 } }")
                .as(CellarStatRecord.class);
    }

}
