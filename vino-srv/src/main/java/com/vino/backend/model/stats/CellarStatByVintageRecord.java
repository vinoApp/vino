package com.vino.backend.model.stats;

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
        return "CellarStatRecord{" +
                "vintage=" + vintage +
                ", count=" + count +
                '}';
    }
}
