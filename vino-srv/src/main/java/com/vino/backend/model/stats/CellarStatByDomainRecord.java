package com.vino.backend.model.stats;

import com.vino.backend.model.WineDomain;
import com.vino.backend.reference.Reference;

import java.math.BigDecimal;

public class CellarStatByDomainRecord {

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
