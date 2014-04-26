package com.vino.backend.model;

import org.joda.time.DateTime;

public class Movement extends Entity {

    public enum Type {
        IN, OUT
    }

    private Type type;

    private WineCellarRecord record;

    private int amount;

    private DateTime date;

    public Type getType() {
        return type;
    }

    public WineCellarRecord getRecord() {
        return record;
    }

    public int getAmount() {
        return amount;
    }

    public Movement setAmount(final int amount) {
        this.amount = amount;
        return this;
    }

    public Movement setType(final Type type) {
        this.type = type;
        return this;
    }

    public Movement setRecord(final WineCellarRecord record) {
        this.record = record;
        return this;
    }

    public DateTime getDate() {
        return date;
    }

    public Movement setDate(final DateTime date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "type=" + type +
                ", record=" + record +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
