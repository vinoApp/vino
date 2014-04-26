package com.vino.backend.model.stats;

import com.vino.backend.model.WineDomain;
import com.vino.backend.reference.Reference;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.math.BigDecimal;

public class MovementStatRecord {

    @Id
    @ObjectId
    private String key;

    private Reference<WineDomain> domain;

    private BigDecimal count;

    public String getKey() {
        return key;
    }

    public MovementStatRecord setKey(final String key) {
        this.key = key;
        return this;
    }

    public Reference<WineDomain> getDomain() {
        return domain;
    }

    public BigDecimal getCount() {
        return count;
    }

    public MovementStatRecord setCount(final BigDecimal count) {
        this.count = count;
        return this;
    }

    public MovementStatRecord setDomain(final Reference<WineDomain> domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public String toString() {
        return "MovementStatRecord{" +
                "key='" + key + '\'' +
                ", domain=" + domain +
                ", count=" + count +
                '}';
    }
}
