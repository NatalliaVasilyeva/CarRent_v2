package com.dmdev.natalliavasilyeva.domain.jpa;

import java.io.Serializable;
import java.time.Instant;

public class UserDetails implements Entity, Serializable {

    private long id;
    private long userId;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private Instant birthday;
    private Instant registrationDate;

    public UserDetails() {
    }

    private UserDetails(long id,
                        long userId,
                        String name,
                        String surname,
                        String address,
                        String phone,
                        Instant birthday,
                        Instant registrationDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.registrationDate = registrationDate;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public long getUser() {
        return userId;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public static final class Builder {
        private long id;
        private long userId;
        private String name;
        private String surname;
        private String address;
        private String phone;
        private Instant birthday;
        private Instant registrationDate;

        public Builder() {
            this.registrationDate = Instant.now();
        }

        public Builder id(long id) {
            this.id = id;
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

        public Builder user(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder registrationDate(Instant registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }


        public UserDetails build() {
            UserDetails userDetails = new UserDetails();
            userDetails.id = this.id;
            userDetails.userId = this.userId;
            userDetails.name = this.name;
            userDetails.surname = this.surname;
            userDetails.address = this.address;
            userDetails.phone = this.phone;
            userDetails.birthday = this.birthday;
            userDetails.registrationDate = this.registrationDate;

            return userDetails;
        }

    }
}