package com.dmdev.natalliavasilyeva.domain.model;

import java.util.Objects;

public class UserLogin implements Identifiable {

    private String email;
    private String login;
    private String password;

    public UserLogin() {

    }

    private UserLogin(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLogin userLogin = (UserLogin) o;
        return Objects.equals(email, userLogin.email) && Objects.equals(login, userLogin.login) && Objects.equals(password, userLogin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, login, password);
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static final class Builder {
        private String email;

        private String login;
        private String password;

        public Builder() {
                //default
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public UserLogin build() {
            UserLogin user = new UserLogin();
            user.email = this.email;
            user.login = this.login;
            user.password = password;
            return user;
        }
    }
}