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

package com.vino.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:45 PM
 */
public class WineCellar {

    private List<Record> records;

    @JsonCreator
    public WineCellar(@JsonProperty("records") List<Record> records) {
        super();
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        return "WineCellar{" +
                "records=" + records +
                '}';
    }

    public class Record {

        private WineBottle bottle;

        private int quantity;

        public Record(WineBottle bottle, int quantity) {
            this.bottle = bottle;
            this.quantity = quantity;
        }

        public WineBottle getBottle() {
            return bottle;
        }

        public int getQuantity() {
            return quantity;
        }

        public Record setBottle(final WineBottle bottle) {
            this.bottle = bottle;
            return this;
        }

        public Record setQuantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "bottle=" + bottle +
                    ", quantity=" + quantity +
                    '}';
        }
    }

}
