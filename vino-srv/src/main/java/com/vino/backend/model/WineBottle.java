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
 * Date: 1/26/14
 * Time: 12:51 PM
 */
public class WineBottle extends Entity {

    private Barcode code;

    private Reference<WineDomain> domain;

    private int vintage;

    public WineBottle() {
    }

    public Barcode getCode() {
        return code;
    }

    public Reference<WineDomain> getDomain() {
        return domain;
    }

    public WineBottle setCode(final Barcode code) {
        this.code = code;
        return this;
    }

    public WineBottle setDomain(final Reference<WineDomain> domain) {
        this.domain = domain;
        return this;
    }

    public int getVintage() {
        return vintage;
    }

    public WineBottle setVintage(final int vintage) {
        this.vintage = vintage;
        return this;
    }

    @Override
    public String toString() {
        return "WineBottle{" +
                "code=" + code +
                ", domain=" + domain +
                ", vintage=" + vintage +
                '}';
    }
}
