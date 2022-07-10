package com.dmdev.natalliavasilyeva.domain.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class User extends ShotUser implements Identifiable {
    private Long userDetailsId;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private Instant birthday;
    private Instant registrationDate;

    private DriverLicense driverLicense;
    private List<Order> orders;

    protected User(Builder builder) {
        super(builder);
        this.userDetailsId = builder.userDetailsId;
        this.name = builder.name;
        this.surname = builder.surname;
        this.address = builder.address;
        this.phone = builder.phone;
        this.birthday = builder.birthday;
        this.registrationDate = builder.registrationDate;
    }


    public Long getUserDetailsId() {
        return userDetailsId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User that = (User) o;
        return Objects.equals(userDetailsId, that.userDetailsId) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(address, that.address) && Objects.equals(phone, that.phone) && Objects.equals(birthday, that.birthday) && Objects.equals(registrationDate, that.registrationDate) && Objects.equals(driverLicense, that.driverLicense) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userDetailsId, name, surname, address, phone, birthday, registrationDate, driverLicense, orders);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "user=" + super.toString() +
                "userDetailsId=" + userDetailsId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", registrationDate=" + registrationDate +
                ", driverLicense=" + driverLicense +
                ", orders=" + orders +
                '}';
    }

    public static class Builder extends ShotUser.Builder<Builder> {

        private Long userDetailsId;
        private String name;
        private String surname;
        private String address;
        private String phone;
        private Instant birthday;
        private Instant registrationDate;

        private DriverLicense driverLicense;
        private List<Order> orders;

        public Builder userDetailsId(Long userDetailsId) {
            this.userDetailsId = userDetailsId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder birthday(Instant birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder registrationDate(Instant registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }
        public Builder driverLicense(DriverLicense driverLicense) {
            this.driverLicense = driverLicense;
            return this;
        }

        public Builder orders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public User build() {

            return new User(this);
        }

    }
}