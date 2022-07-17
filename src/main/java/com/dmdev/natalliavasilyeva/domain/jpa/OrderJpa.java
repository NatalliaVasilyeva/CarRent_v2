package com.dmdev.natalliavasilyeva.domain.jpa;


import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class OrderJpa implements Entity, Serializable {

    private long id;
    private Instant date;
    private long userId;
    private long carId;
    private String passport;
    private boolean isInsuranceNeeded;
    private OrderStatus orderStatus;
    private BigDecimal sum;

    public OrderJpa() {
    }

    private OrderJpa(long id,
                     Instant date,
                     long userId,
                     long carId,
                     String passport,
                     boolean isInsuranceNeeded,
                     OrderStatus orderStatus,
                     BigDecimal sum) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.carId = carId;
        this.passport = passport;
        this.isInsuranceNeeded = isInsuranceNeeded;
        this.orderStatus = orderStatus;
        this.sum = sum;
    }

    public long getId() {
        return id;
    }

    public Instant getDate() {
        return date;
    }

    public long getUserId() {
        return userId;
    }

    public long getCarId() {
        return carId;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public boolean isInsuranceNeeded() {
        return isInsuranceNeeded;
    }

    public void setInsuranceNeeded(boolean insuranceNeeded) {
        isInsuranceNeeded = insuranceNeeded;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public static final class Builder {
        private long id;
        private Instant date;
        private long userId;
        private long carId;
        private String passport;
        private boolean isInsuranceNeeded;
        private OrderStatus orderStatus;
        private BigDecimal sum;

        public Builder() {
            this.date = Instant.now();
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder date(Instant date) {
            this.date = date;
            return this;
        }

        public Builder user(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder car(long carId) {
            this.carId = carId;
            return this;
        }

        public Builder passport(String passport) {
            this.passport = passport;
            return this;
        }

        public Builder insurance(boolean isInsuranceNeeded) {
            this.isInsuranceNeeded = isInsuranceNeeded;
            return this;
        }

        public Builder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder sum(BigDecimal sum) {
            this.sum = sum;
            return this;
        }

        public OrderJpa build() {
            OrderJpa orderJpa = new OrderJpa();
            orderJpa.id = this.id;
            orderJpa.date = this.date;
            orderJpa.userId = this.userId;
            orderJpa.carId = this.carId;
            orderJpa.passport = this.passport;
            orderJpa.isInsuranceNeeded = this.isInsuranceNeeded;
            orderJpa.orderStatus = this.orderStatus;
            orderJpa.sum = this.sum;

            return orderJpa;
        }
    }


}