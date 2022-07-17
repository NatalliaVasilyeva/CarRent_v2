package com.dmdev.natalliavasilyeva.domain.jpa;

import java.io.Serializable;
import java.math.BigDecimal;

public class PriceJpa implements Entity, Serializable {

    private long id;
    private BigDecimal sum;

    public PriceJpa() {
    }

    private PriceJpa(long id, BigDecimal sum) {
        this.id = id;
        this.sum = sum;
    }

    @Override
    public long getId() {
        return id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public static final class Builder {
        private long id;
        private BigDecimal sum;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder sum(BigDecimal sum) {
            this.sum = sum;
            return this;
        }

        public PriceJpa build() {
            PriceJpa priceJpa = new PriceJpa();
            priceJpa.id = this.id;
            priceJpa.sum = this.sum;

            return priceJpa;
        }
    }

}