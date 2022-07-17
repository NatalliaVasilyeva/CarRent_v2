package com.dmdev.natalliavasilyeva.domain.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class AccidentJpa implements Entity, Serializable {
    private long id;
    private long orderId;
    private Instant date;
    private String description;
    private BigDecimal damage;

    public AccidentJpa() {
    }

    private AccidentJpa(long id, long orderId, Instant date, String description, BigDecimal damage) {
        this.id = id;
        this.orderId = orderId;
        this.date = date;
        this.description = description;
        this.damage = damage;
    }

    @Override
    public long getId() {
        return id;
    }

    public long getOrderId() {
        return orderId;
    }

    public Instant getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDamage() {
        return damage;
    }

    public static final class Builder {
        private long id;
        private long orderId;
        private Instant date;
        private String description;
        private BigDecimal damage;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder order(long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder date(Instant date) {
            this.date = date;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder damage(BigDecimal damage) {
            this.damage = damage;
            return this;
        }

        public AccidentJpa build() {
            AccidentJpa accidentJpa = new AccidentJpa();
            accidentJpa.id = this.id;
            accidentJpa.orderId = this.orderId;
            accidentJpa.date = this.date;
            accidentJpa.description = this.description;
            accidentJpa.damage = this.damage;


            return accidentJpa;
        }
    }

}