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

import com.vino.backend.model.origins.WineDomain;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:33 PM
 */
public class WineBottle {

    private int id;

    private String barcode;

    private int vintage;

    private WineDomain domain;

    private String base64Image;

    public WineBottle(String barcode, int vintage, WineDomain domain) {
        this.barcode = barcode;
        this.vintage = vintage;
        this.domain = domain;
    }

    public WineBottle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVintage() {
        return vintage;
    }

    public void setVintage(int vintage) {
        this.vintage = vintage;
    }

    public WineDomain getDomain() {
        return domain;
    }

    public void setDomain(WineDomain domain) {
        this.domain = domain;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    @Override
    public String toString() {
        return "WineBottle{" +
                "id='" + id + '\'' +
                ", barcode='" + barcode + '\'' +
                ", vintage=" + vintage +
                ", domain=" + domain +
                '}';
    }
}
