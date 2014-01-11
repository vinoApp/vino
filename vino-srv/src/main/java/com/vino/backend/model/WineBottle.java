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

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:33 PM
 */
public class WineBottle extends Entity {

    private Barcode barcode;

    private WineDomain domain;

    private int vintage;

    public WineBottle(Barcode barcode, WineDomain domain, int vintage) {
        super();
        this.barcode = barcode;
        this.domain = domain;
        this.vintage = vintage;
    }

    public WineBottle() {
    }

    public int getVintage() {
        return vintage;
    }

    public WineBottle setVintage(int vintage) {
        this.vintage = vintage;
        return this;
    }

    public WineDomain getDomain() {
        return domain;
    }

    public WineBottle setDomain(WineDomain domain) {
        this.domain = domain;
        return this;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public WineBottle setBarcode(Barcode barcode) {
        this.barcode = barcode;
        return this;
    }

    @Override
    public String toString() {
        return "WineBottle{" +
                "barcode=" + barcode +
                ", domain=" + domain +
                ", vintage=" + vintage +
                '}';
    }
}
