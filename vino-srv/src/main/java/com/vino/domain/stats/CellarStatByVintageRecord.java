/*
 *
 *  * Copyright 2013 - Elian ORIOU
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.vino.domain.stats;

import java.math.BigDecimal;

public class CellarStatByVintageRecord {

    private int vintage;

    private BigDecimal count;

    public int getVintage() {
        return vintage;
    }

    public BigDecimal getCount() {
        return count;
    }

    public CellarStatByVintageRecord setVintage(final int vintage) {
        this.vintage = vintage;
        return this;
    }

    public CellarStatByVintageRecord setCount(final BigDecimal count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return "CellarStatByVintageRecord{" +
                "vintage=" + vintage +
                ", count=" + count +
                '}';
    }
}