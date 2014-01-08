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

package model;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 3:37 PM
 */
public class WineDomain extends Entity {

    private String name;

    private WineAOC origin;

    private String sticker;

    public WineDomain() {

    }

    public WineDomain(String name, WineAOC origin) {
        this.name = name;
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public WineDomain setName(String name) {
        this.name = name;
        return this;
    }

    public WineAOC getOrigin() {
        return origin;
    }

    public WineDomain setOrigin(WineAOC origin) {
        this.origin = origin;
        return this;
    }

    public String getSticker() {
        return sticker;
    }

    public WineDomain setSticker(String sticker) {
        this.sticker = sticker;
        return this;
    }

    @Override
    public String toString() {
        return "WineDomain{" +
                "name='" + name + '\'' +
                ", origin=" + origin +
                ", sticker='" + sticker + '\'' +
                '}';
    }
}
