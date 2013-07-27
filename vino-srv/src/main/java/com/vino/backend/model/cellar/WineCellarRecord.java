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

package com.vino.backend.model.cellar;

import com.vino.backend.model.WineBottle;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:45 PM
 */
public class WineCellarRecord {

    private WineBottle bottle;

    private int quantity;

    public WineCellarRecord() {
    }

    public WineCellarRecord(WineBottle bottle, int quantity) {
        this.bottle = bottle;
        this.quantity = quantity;
    }

    public WineBottle getBottle() {
        return bottle;
    }

    public void setBottle(WineBottle bottle) {
        this.bottle = bottle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "WineCellarRecord{" +
                "bottle=" + bottle +
                ", quantity=" + quantity +
                '}';
    }
}
