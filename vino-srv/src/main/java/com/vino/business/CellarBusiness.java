/*
 *
 *  * Copyright 2013 - Elian ORIOU
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.vino.business;

import com.google.common.base.Optional;
import com.vino.domain.*;
import com.vino.persistence.Persistor;
import com.vino.repositories.CellarRepository;
import org.joda.time.DateTime;
import restx.factory.Component;

@Component
public class CellarBusiness {

    private final Persistor persistor;
    private final CellarRepository repository;

    public CellarBusiness(Persistor persistor,
                          CellarRepository repository) {
        this.persistor = persistor;
        this.repository = repository;
    }

    public WineCellar createCellar(WineCellar cellar) {
        return persistor.persist(cellar);
    }

    public WineCellar removeCellar(String cellarKey) {
        persistor.delete(cellarKey);
        return (WineCellar) new WineCellar().setKey(cellarKey);
    }

    public Movement onCellarMovement(String cellarKey, Movement movement) {

        if (movement.getRecord() == null) {
            throw new IllegalStateException("Attached record not found !");
        }

        // Set the date associated to the movement
        movement.setDate(DateTime.now());

        // Persist movement (history)
        persistor.persist(movement);

        // Persist cellar record
        WineCellarRecord record = movement.getRecord();

        if (movement.getMovementType() == Movement.Type.IN) {
            addInCellar(Reference.<WineCellar>of(cellarKey), record, movement.getAmount());
        } else if (movement.getMovementType() == Movement.Type.OUT) {
            removeFromCellar(record.getKey(), movement.getAmount());
        }

        return movement;
    }

    private boolean addInCellar(Reference<WineCellar> cellar, WineCellarRecord record, int quantity) {

        if (cellar == null
                || record.getCode() == null
                || record.getDomain() == null
                || record.getVintage() == 0) {
            throw new IllegalArgumentException("Missing cellar | code | domain | vintage on the provided record");
        }

        Optional<WineCellarRecord> foundRecord = repository.getRecord(cellar, record.getDomain(), record.getVintage());

        if (foundRecord.isPresent()) {
            if (foundRecord.get().getQuantity() + quantity <= 0) {
                persistor.delete(foundRecord.get().getKey());
            } else {
                foundRecord.get().setQuantity(foundRecord.get().getQuantity() + quantity);
                persistor.persist(foundRecord.get(), false);
            }
        } else {
            // Aggregate data
            Reference<WineAOC> aoc = record.getDomain().get().get().getOrigin();
            Reference<WineRegion> region = aoc.get().get().getRegion();
            record.setAoc(aoc);
            record.setRegion(region);
            // Persist entity
            record.setCellar(cellar);
            record.setQuantity(quantity);
            persistor.persist(record);
        }

        return true;
    }

    private boolean removeFromCellar(String key, int quantity) {

        Optional<WineCellarRecord> record = persistor.getEntity(key);

        if (!record.isPresent()) {
            return false;
        }

        if (record.get().getQuantity() - quantity <= 0) {
            persistor.delete(record.get().getKey());
        } else {
            record.get().setQuantity(record.get().getQuantity() - quantity);
            persistor.persist(record.get(), false);
        }

        return true;
    }
}
