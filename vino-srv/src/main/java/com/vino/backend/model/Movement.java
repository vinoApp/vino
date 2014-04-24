package com.vino.backend.model;

public class Movement extends Entity {

    public enum Type {
        IN, OUT
    }

    private Type type;

    private WineCellarRecord record;

    private int amount;

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

    @Override
    public String toString() {
        return "Movement{" +
                "type=" + type +
                ", record=" + record +
                ", amount=" + amount +
                '}';
    }
}
