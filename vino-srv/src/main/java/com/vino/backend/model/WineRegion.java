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
 * Time: 4:24 PM
 */
public class WineRegion extends Entity {

    private String name;

    public WineRegion() {
        super();
    }

    public WineRegion(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public WineRegion setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "WineRegion{" +
                "name='" + name + '\'' +
                '}';
    }
}
