package com.vino.domain;

import org.joda.time.DateTime;

public class Movement extends Entity {

    public enum Type {
        IN, OUT
    }

    private Type movementType;

    private WineCellarRecord record;

    private int amount;

    private DateTime date;

    public Type getMovementType() {
        return movementType;
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

    public Movement setMovementType(final Type type) {
        this.movementType = type;
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
    public EntityType getType() {
        return EntityType.MOVEMENT;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "movementType=" + movementType +
                ", record=" + record +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
