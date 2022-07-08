package com.dmdev.natalliavasilyeva.domain.model;


import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User implements Identifiable {

    private Long id;

    private String login;
    private String email;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private Instant birthday;
    private DriverLicense driverLicense;
    private List<Order> orders;

    public User() {
    }

    private User(Long id, String login, String email, String password, String role, String name, String surname, String address, String phone, Instant birthday, DriverLicense driverLicense, List<Order> orders) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.driverLicense = driverLicense;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
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

    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(address, user.address) && Objects.equals(phone, user.phone) && Objects.equals(birthday, user.birthday) && Objects.equals(driverLicense, user.driverLicense) && Objects.equals(orders, user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, password, role, name, surname, address, phone, birthday, driverLicense, orders);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", driverLicense=" + driverLicense +
                ", orders=" + orders +
                '}';
    }

    public static final class Builder {
        private Long id;

        private String login;
        private String email;
        private String password;
        private String role;
        private String name;
        private String surname;
        private String address;
        private String phone;
        private Instant birthday;
        private DriverLicense driverLicense;
        private List<Order> orders;

        public Builder() {
            this.orders = Collections.emptyList();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
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

        public Builder driverLicense(DriverLicense driverLicense) {
            this.driverLicense = driverLicense;
            return this;
        }

        public Builder orders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public User build() {
            User user = new User();
            user.id = this.id;
            user.login = this.login;
            user.email = this.email;
            user.password = password;
            user.role = this.role;
            user.name = this.name;
            user.surname = this.surname;
            user.address = this.address;
            user.phone = this.phone;
            user.birthday = this.birthday;
            user.driverLicense = this.driverLicense;
            user.orders = this.orders;

            return user;
        }
    }
}