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

import com.vino.backend.reference.Reference;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:45 PM
 */
public class WineCellarRecord extends Entity {

    private Barcode code;

    private Reference<WineCellar> cellar;

    private Reference<WineDomain> domain;

    private Reference<WineAOC> aoc;

    private Reference<WineRegion> region;

    private int vintage;

    private int quantity;

    public Barcode getCode() {
        return code;
    }

    public WineCellarRecord setCode(final Barcode code) {
        this.code = code;
        return this;
    }

    public Reference<WineCellar> getCellar() {
        return cellar;
    }

    public WineCellarRecord setCellar(final Reference<WineCellar> cellar) {
        this.cellar = cellar;
        return this;
    }

    public Reference<WineDomain> getDomain() {
        return domain;
    }

    public WineCellarRecord setDomain(final Reference<WineDomain> domain) {
        this.domain = domain;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public WineCellarRecord setQuantity(final int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getVintage() {
        return vintage;
    }

    public WineCellarRecord setVintage(final int vintage) {
        this.vintage = vintage;
        return this;
    }

    public Reference<WineAOC> getAoc() {
        return aoc;
    }

    public Reference<WineRegion> getRegion() {
        return region;
    }

    public WineCellarRecord setRegion(final Reference<WineRegion> region) {
        this.region = region;
        return this;
    }

    public WineCellarRecord setAoc(final Reference<WineAOC> aoc) {
        this.aoc = aoc;
        return this;
    }

    @Override
    public String toString() {
        return "WineCellarRecord{" +
                "code=" + code +
                ", cellar=" + cellar +
                ", domain=" + domain +
                ", aoc=" + aoc +
                ", region=" + region +
                ", vintage=" + vintage +
                ", quantity=" + quantity +
                '}';
    }
}
