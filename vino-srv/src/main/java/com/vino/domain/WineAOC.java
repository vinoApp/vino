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

package com.vino.domain;

/**
 * User: walien
 * Date: 7/27/13
 * Time: 4:24 PM
 */
public class WineAOC extends Entity {

    private String name;

    private Reference<WineRegion> region;

    public WineAOC() {
        super();
    }

    public String getName() {
        return name;
    }

    public WineAOC setName(String name) {
        this.name = name;
        return this;
    }

    public Reference<WineRegion> getRegion() {
        return region;
    }

    public WineAOC setRegion(Reference<WineRegion> region) {
        this.region = region;
        return this;
    }

    @Override
    public EntityType getType() {
        return EntityType.AOC;
    }

    @Override
    public String toString() {
        return "WineAOC{" +
                "name='" + name + '\'' +
                ", region=" + region +
                '}';
    }
}
