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

package com.vino.backend.db;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User: eoriou
 * Date: 09/03/2014
 * Time: 10:54
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WineDBRecord {

    private String id;

    private String name;

    private String aoc;

    private String urlpicture;

    private String color;

    private String shortname;

    private String complement;

    private String html;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAoc() {
        return aoc;
    }

    public void setAoc(String aoc) {
        this.aoc = aoc;
    }

    public String getUrlpicture() {
        return urlpicture;
    }

    public void setUrlpicture(String urlpicture) {
        this.urlpicture = urlpicture;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getHtml() {
        return html;
    }

    public WineDBRecord setHtml(final String html) {
        this.html = html;
        return this;
    }

    @Override
    public String toString() {
        return "WineDBRecord{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", aoc='" + aoc + '\'' +
                ", urlpicture='" + urlpicture + '\'' +
                ", color='" + color + '\'' +
                ", shortname='" + shortname + '\'' +
                ", complement='" + complement + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}
