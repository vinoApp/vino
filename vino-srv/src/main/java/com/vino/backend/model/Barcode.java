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
 * Date: 1/12/14
 * Time: 12:23 AM
 */
public class Barcode {

    public enum Type {
        EAN_13
    }

    private Type type;

    private String value;

    private Barcode(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public static Barcode ean13(String value) {
        return new Barcode(Type.EAN_13, value);
    }

    @Override
    public String toString() {
        return "Barcode{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
