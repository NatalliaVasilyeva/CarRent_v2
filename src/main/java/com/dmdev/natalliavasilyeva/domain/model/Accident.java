package com.dmdev.natalliavasilyeva.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Accident implements Identifiable {

    private Long id;
    private Long orderNumber;
    private Instant date;
    private String brand;
    private String model;
    private String carNumber;
    private String userFirstName;
    private String userLastName;
    private String description;
    private BigDecimal damage;

    private Error error;

    public Accident() {
    }

    private Accident(Long id,
                     long orderId,
                     Instant date,
                     String brand,
                     String model,
                     String carNumber,
                     String userFirstName,
                     String userLastName,
                     String description,
                     BigDecimal damage,
                     Error error) {
        this.id = id;
        this.orderNumber = orderId;
        this.date = date;
        this.brand = brand;
        this.model = model;
        this.carNumber = carNumber;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.description = description;
        this.damage = damage;
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public Instant getDate() {
        return date;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getDamage() {
        return damage;
    }

    public Error getError() {
        return error;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accident accident = (Accident) o;
        return Objects.equals(id, accident.id) && Objects.equals(orderNumber, accident.orderNumber) && Objects.equals(date, accident.date) && Objects.equals(brand, accident.brand) && Objects.equals(model, accident.model) && Objects.equals(carNumber, accident.carNumber) && Objects.equals(userFirstName, accident.userFirstName) && Objects.equals(userLastName, accident.userLastName) && Objects.equals(description, accident.description) && Objects.equals(damage, accident.damage) && Objects.equals(error, accident.error);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, date, brand, model, carNumber, userFirstName, userLastName, description, damage, error);
    }

    @Override
    public String toString() {
        return "Accident{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", date=" + date +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", description='" + description + '\'' +
                ", damage=" + damage +
                ", error=" + error +
                '}';
    }
    public static final class Builder {

        private Long id;
        private Long orderId;
        private Instant date;
        private String brand;
        private String model;
        private String carNumber;
        private String userFirstName;
        private String userLastName;
        private String description;
        private BigDecimal damage;

        private Error error;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder order(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder date(Instant date) {
            this.date = date;
            return this;
        }
        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder car(String carNumber) {
            this.carNumber = carNumber;
            return this;
        }

        public Builder userName(String userFirstName) {
            this.userFirstName = userFirstName;
            return this;
        }

        public Builder userSurname(String userLastName) {
            this.userLastName = userLastName;
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

        public Builder error(Error error) {
            this.error = error;
            return this;
        }

        public Accident build() {
            Accident accident = new Accident();
            accident.id = id;
            accident.orderNumber = this.orderId;
            accident.date = this.date;
            accident.brand = brand;
            accident.model = model;
            accident.carNumber = carNumber;
            accident.userFirstName = userFirstName;
            accident.userLastName = userLastName;
            accident.description = this.description;
            accident.damage = this.damage;
            accident.error = this.error;

            return accident;
        }
    }

}