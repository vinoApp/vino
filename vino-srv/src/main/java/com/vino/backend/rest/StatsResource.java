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
    private final JongoCollection records;

    public StatsResource(@Named("movements") JongoCollection movements,
                         @Named("records") JongoCollection records) {
        this.movements = movements;
        this.records = records;
    }

    @GET("/stats/mostConsumedDomain")
    public Iterable<MovementStatRecord> getMostConsumedWineDomain() {

        return movements.get()
                .aggregate("{ $match : { type : # }}", "OUT")
                .and("{ $group : { _id: '$record.domain', count: { $sum : 1 } } }")
                .and("{ $project : { domain: '$_id', count: 1 } }")
                .as(MovementStatRecord.class);
    }

    @GET("/stats/{cellarKey}/cellarStockByVintage")
    public Iterable<CellarStatRecord.CellarStatByVintageRecord> getCellarStockByVintage(String cellarKey) {

        if (true) {
            throw new RuntimeException("TRUC");
        }

        return records.get()
                .aggregate("{ $match : { cellar : # }}", cellarKey)
                .and("{ $group : { _id: '$vintage', count: { $sum : '$quantity' } } }")
                .and("{ $project : { vintage: '$_id', count: 1 } }")
                .as(CellarStatRecord.CellarStatByVintageRecord.class);
    }

    @GET("/stats/{cellarKey}/cellarStockByDomain")
    public Iterable<CellarStatRecord.CellarStatByDomainRecord> getCellarStockByDomain(String cellarKey) {

        return records.get()
                .aggregate("{ $match : { cellar : # }}", cellarKey)
                .and("{ $group : { _id: '$domain', count: { $sum : '$quantity' } } }")
                .and("{ $project : { domain: '$_id', count: 1 } }")
                .as(CellarStatRecord.CellarStatByDomainRecord.class);
    }

    @GET("/stats/{cellarKey}/cellarStockByAOC")
    public Iterable<CellarStatRecord.CellarStatByAOCRecord> getCellarStockByAOC(String cellarKey) {

        return records.get()
                .aggregate("{ $match : { cellar : # }}", cellarKey)
                .and("{ $group : { _id: '$aoc', count: { $sum : '$quantity' } } }")
                .and("{ $project : { aoc: '$_id', count: 1 } }")
                .as(CellarStatRecord.CellarStatByAOCRecord.class);
    }

}
