package com.dmdev.natalliavasilyeva.domain.model;

import java.time.Instant;
import java.util.Objects;

public class CarRentalTime implements Identifiable {

    private Long id;
    private Order order;
    private Instant startRentalDate;
    private Instant endRentalDate;

    public CarRentalTime() {
    }

    private CarRentalTime(Long id, Order order, Instant startRentalDate, Instant endRentalDate) {
        this.id = id;
        this.order = order;
        this.startRentalDate = startRentalDate;
        this.endRentalDate = endRentalDate;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Instant getStartRentalDate() {
        return startRentalDate;
    }

    public Instant getEndRentalDate() {
        return endRentalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRentalTime that = (CarRentalTime) o;
        return Objects.equals(id, that.id) && Objects.equals(order, that.order) && Objects.equals(startRentalDate, that.startRentalDate) && Objects.equals(endRentalDate, that.endRentalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, startRentalDate, endRentalDate);
    }

    @Override
    public String toString() {
        return "CarRentalTime{" +
                "id=" + id +
                ", order=" + order +
                ", startRentalDate=" + startRentalDate +
                ", endRentalDate=" + endRentalDate +
                '}';
    }

    public static final class Builder {

        private Long id;
        private Order order;
        private Instant startRentalDate;
        private Instant endRentalDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
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
            rentalTime.order = this.order;
            rentalTime.startRentalDate = this.startRentalDate;
            rentalTime.endRentalDate = this.endRentalDate;

            return rentalTime;
        }
    }
}