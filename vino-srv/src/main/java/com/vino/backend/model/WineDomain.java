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

import com.fasterxml.jackson.annotation.JsonView;
import com.vino.backend.persistence.mongo.Views;
import com.vino.backend.reference.Reference;

import java.util.Arrays;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:37 PM
 */
public class WineDomain extends Entity {

    private String name;

    private Reference<WineAOC> origin;

    @JsonView({ restx.jackson.Views.Private.class, Views.Details.class, Views.Edit.class })
    private byte[] sticker;

    @JsonView({ restx.jackson.Views.Private.class, Views.Details.class, Views.Edit.class })
    private String localization;

    @JsonView({ restx.jackson.Views.Private.class, Views.Details.class, Views.Edit.class })
    private WineDomain.Wine wineDescription;

    @JsonView({ restx.jackson.Views.Private.class, Views.Details.class, Views.Edit.class })
    private WineDomain.Tasting tasting;

    @JsonView({ restx.jackson.Views.Private.class, Views.Details.class, Views.Edit.class })
    private WineDomain.History history;

    public WineDomain() {

    }

    public String getName() {
        return name;
    }

    public WineDomain setName(String name) {
        this.name = name;
        return this;
    }

    public Reference<WineAOC> getOrigin() {
        return origin;
    }

    public WineDomain setOrigin(Reference<WineAOC> origin) {
        this.origin = origin;
        return this;
    }

    public byte[] getSticker() {
        return sticker;
    }

    public String getLocalization() {
        return localization;
    }

    public WineDomain setLocalization(final String localization) {
        this.localization = localization;
        return this;
    }

    public WineDomain setSticker(byte[] sticker) {
        this.sticker = sticker;
        return this;
    }

    public Wine getWineDescription() {
        return wineDescription;
    }

    public WineDomain setWineDescription(final Wine wineDescription) {
        this.wineDescription = wineDescription;
        return this;
    }

    public Tasting getTasting() {
        return tasting;
    }

    public WineDomain setTasting(final Tasting tasting) {
        this.tasting = tasting;
        return this;
    }

    public History getHistory() {
        return history;
    }

    public WineDomain setHistory(final History history) {
        this.history = history;
        return this;
    }

    @Override
    public String toString() {
        return "WineDomain{" +
                "name='" + name + '\'' +
                ", origin=" + origin +
                ", sticker=" + Arrays.toString(sticker) +
                ", wineDescription=" + wineDescription +
                ", tasting=" + tasting +
                ", history=" + history +
                '}';
    }

    public static class Wine {

        public String floor;

        public String grape;

        public String medal;

    }

    public static class Tasting {

        public String comment;

        public String garde;

        public String temperature;

    }

    public static class History {

        public String description;

    }
}
