package com.dmdev.natalliavasilyeva.domain.model;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Order implements Identifiable {

    private Long id;
    private Instant date;
    private CarRentalTime carRentalTime;
    private Car car;
    private String passport;
    private boolean isInsuranceNeeded;
    private OrderStatus orderStatus;
    private BigDecimal sum;

    public Order() {
    }

    private Order(Long id, Instant date, CarRentalTime carRentalTime, Car car, String passport, boolean isInsuranceNeeded, OrderStatus orderStatus, BigDecimal sum) {
        this.id = id;
        this.date = date;
        this.carRentalTime = carRentalTime;
        this.car = car;
        this.passport = passport;
        this.isInsuranceNeeded = isInsuranceNeeded;
        this.orderStatus = orderStatus;
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public CarRentalTime getCarRentalTime() {
        return carRentalTime;
    }

    public Car getCar() {
        return car;
    }

    public String getPassport() {
        return passport;
    }

    public boolean isInsuranceNeeded() {
        return isInsuranceNeeded;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public Instant getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return isInsuranceNeeded == order.isInsuranceNeeded && Objects.equals(id, order.id) && Objects.equals(date, order.date) && Objects.equals(carRentalTime, order.carRentalTime) && Objects.equals(car, order.car) && Objects.equals(passport, order.passport) && orderStatus == order.orderStatus && Objects.equals(sum, order.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, carRentalTime, car, passport, isInsuranceNeeded, orderStatus, sum);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", carRentalTime=" + carRentalTime +
                ", car=" + car +
                ", passport='" + passport + '\'' +
                ", isInsuranceNeeded=" + isInsuranceNeeded +
                ", orderStatus=" + orderStatus +
                ", sum=" + sum +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Instant date;
        private CarRentalTime carRentalTime;
        private Car car;
        private String passport;
        private boolean isInsuranceNeeded;
        private OrderStatus orderStatus;
        private BigDecimal sum;

        public Builder() {
            this.date = Instant.now();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder date(Instant date) {
            this.date = date;
            return this;
        }

        public Builder carRentalTime(CarRentalTime carRentalTime) {
            this.carRentalTime = carRentalTime;
            return this;
        }

        public Builder car(Car car) {
            this.car = car;
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

        public Order build() {
            Order order = new Order();
            order.id = this.id;
            order.date = this.date;
            order.carRentalTime = this.carRentalTime;
            order.car = this.car;
            order.passport = this.passport;
            order.isInsuranceNeeded = this.isInsuranceNeeded;
            order.orderStatus = this.orderStatus;
            order.sum = this.sum;

            return order;
        }
    }


}