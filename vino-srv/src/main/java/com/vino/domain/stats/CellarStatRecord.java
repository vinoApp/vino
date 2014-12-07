package com.vino.domain.stats;

import com.vino.domain.WineDomain;
import com.vino.domain.Reference;

import java.math.BigDecimal;

public class CellarStatRecord {

    // STOCK BY VINTAGE STATS
    public static class CellarStatByVintageRecord {

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

    // STOCK BY AOC STATS
    public static class CellarStatByAOCRecord {

        private Reference<WineDomain> aoc;

        private BigDecimal count;

        public Reference<WineDomain> getAoc() {
            return aoc;
        }

        public CellarStatByAOCRecord setAoc(final Reference<WineDomain> aoc) {
            this.aoc = aoc;
            return this;
        }

        public BigDecimal getCount() {
            return count;
        }

        public CellarStatByAOCRecord setCount(final BigDecimal count) {
            this.count = count;
            return this;
        }

        @Override
        public String toString() {
            return "CellarStatByAOCRecord{" +
                    "aoc=" + aoc +
                    ", count=" + count +
                    '}';
        }
    }

    // STOCK BY DOMAIN STATS
    public static class CellarStatByDomainRecord {

        private Reference<WineDomain> domain;

        private BigDecimal count;

        public Reference<WineDomain> getDomain() {
            return domain;
        }

        public CellarStatByDomainRecord setDomain(final Reference<WineDomain> domain) {
            this.domain = domain;
            return this;
        }

        public BigDecimal getCount() {
            return count;
        }

        public CellarStatByDomainRecord setCount(final BigDecimal count) {
            this.count = count;
            return this;
        }

        @Override
        public String toString() {
            return "CellarStatByDomainRecord{" +
                    "domain=" + domain +
                    ", count=" + count +
                    '}';
        }
    }

}
