package com.dmdev.natalliavasilyeva.domain.model;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Order implements Identifiable {

    private Long id;
    private Instant date;
    private CarRentalTime carRentalTime;
    private Car car;

    private User user;
    private String passport;
    private boolean isInsuranceNeeded;
    private OrderStatus orderStatus;
    private BigDecimal sum;
    private boolean withAccident;

    public Order() {
    }

    private Order(Long id, Instant date, CarRentalTime carRentalTime, Car car, User user, String passport, boolean isInsuranceNeeded, OrderStatus orderStatus, BigDecimal sum, boolean withAccident) {
        this.id = id;
        this.date = date;
        this.carRentalTime = carRentalTime;
        this.car = car;
        this.user = user;
        this.passport = passport;
        this.isInsuranceNeeded = isInsuranceNeeded;
        this.orderStatus = orderStatus;
        this.sum = sum;
        this.withAccident = withAccident;
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

    public User getUser() {
        return user;
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

    public boolean isWithAccident() {
        return withAccident;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return isInsuranceNeeded == order.isInsuranceNeeded && withAccident == order.withAccident && Objects.equals(id, order.id) && Objects.equals(date, order.date) && Objects.equals(carRentalTime, order.carRentalTime) && Objects.equals(car, order.car) && Objects.equals(user, order.user) && Objects.equals(passport, order.passport) && orderStatus == order.orderStatus && Objects.equals(sum, order.sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, carRentalTime, car, user, passport, isInsuranceNeeded, orderStatus, sum, withAccident);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", carRentalTime=" + carRentalTime +
                ", car=" + car +
                ", user=" + user +
                ", passport='" + passport + '\'' +
                ", isInsuranceNeeded=" + isInsuranceNeeded +
                ", orderStatus=" + orderStatus +
                ", sum=" + sum +
                ", withAccident=" + withAccident +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Instant date;
        private CarRentalTime carRentalTime;
        private Car car;

        private User user;
        private String passport;
        private boolean isInsuranceNeeded;
        private OrderStatus orderStatus;
        private BigDecimal sum;

        private boolean withAccident;

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

        public Builder user(User user) {
            this.user = user;
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

        public Builder withAccidence(boolean withAccident) {
            this.withAccident = withAccident;
            return this;
        }


        public Order build() {
            Order order = new Order();
            order.id = this.id;
            order.date = this.date;
            order.carRentalTime = this.carRentalTime;
            order.car = this.car;
            order.user = this.user;
            order.passport = this.passport;
            order.isInsuranceNeeded = this.isInsuranceNeeded;
            order.orderStatus = this.orderStatus;
            order.sum = this.sum;
            order.withAccident = this.withAccident;

            return order;
        }
    }


}