package com.dmdev.natalliavasilyeva.domain.jpa;

import java.io.Serializable;
import java.time.Instant;

public class CarRentalTime implements Entity, Serializable {

    private long id;
    private long orderId;
    private Instant startRentalDate;
    private Instant endRentalDate;

    public CarRentalTime() {
    }

    private CarRentalTime(long id, long orderId, Instant startRentalDate, Instant endRentalDate) {
        this.id = id;
        this.orderId = orderId;
        this.startRentalDate = startRentalDate;
        this.endRentalDate = endRentalDate;
    }

    @Override
    public long getId() {
        return id;
    }

    public long getOrderId() {
        return orderId;
    }

    public Instant getStartRentalDate() {
        return startRentalDate;
    }

    public void setStartRentalDate(Instant startRentalDate) {
        this.startRentalDate = startRentalDate;
    }

    public Instant getEndRentalDate() {
        return endRentalDate;
    }

    public void setEndRentalDate(Instant endRentalDate) {
        this.endRentalDate = endRentalDate;
    }

    public static final class Builder {

        private long id;
        private long orderId;
        private Instant startRentalDate;
        private Instant endRentalDate;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder order(long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder start(Instant startRentalDate) {
            this.startRentalDate = startRentalDate;
            return this;
        }

        public Builder end(Instant endRentalDate) {
            this.endRentalDate = endRentalDate;
            return this;
        }

        public CarRentalTime build() {
            CarRentalTime rentalTime = new CarRentalTime();
            rentalTime.id = this.id;
            rentalTime.orderId = this.orderId;
            rentalTime.startRentalDate = this.startRentalDate;
            rentalTime.endRentalDate = this.endRentalDate;

            return rentalTime;
        }
    }
}