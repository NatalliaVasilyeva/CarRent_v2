package com.dmdev.natalliavasilyeva.domain.model;

import java.util.Objects;

public class ShotUser implements Identifiable {
    private Long userId;
    private String login;
    private String email;
    private String password;
    private Role role;

    protected ShotUser(Builder<?> builder) {
        this.userId = builder.userId;
        this.login = builder.login;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
    }

    public Long getId() {
        return userId;
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

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShotUser user = (ShotUser) o;
        return Objects.equals(userId, user.userId) && Objects.equals(login, user.login) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, email, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public static class Builder<T extends Builder<T>> {
        private Long userId;
        private String login;
        private String email;
        private String password;
        private Role role;

        public T userId(Long userId) {
            this.userId = userId;
            return (T) this;
        }

        public T login(String login) {
            this.login = login;
            return (T) this;
        }

        public T email(String email) {
            this.email = email;
            return (T) this;
        }

        public T password(String password) {
            this.password = password;
            return (T) this;
        }

        public T role(Role role) {
            this.role = role;
            return (T) this;
        }

        public ShotUser build() {

            return new ShotUser(this);
        }
    }
}