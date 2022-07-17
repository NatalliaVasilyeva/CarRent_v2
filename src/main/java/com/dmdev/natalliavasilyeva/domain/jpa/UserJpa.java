package com.dmdev.natalliavasilyeva.domain.jpa;


import com.dmdev.natalliavasilyeva.domain.model.Role;

import java.io.Serializable;

public class UserJpa implements Entity, Serializable {
    private long id;

    private String login;
    private String email;
    private String password;
    private String role;

    public UserJpa() {
    }

    private UserJpa(long id, String login, String email, String password, String role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public long getId() {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static final class Builder {

        private final String DEFAULT_USER_ROLE = Role.CLIENT.name();
        private long id;
        private String login;
        private String email;
        private String password;
        private String role;

        public Builder() {
            this.role = DEFAULT_USER_ROLE;
        }

        public Builder id(long id) {
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
            this.password = email;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public UserJpa build() {
            UserJpa userJpa = new UserJpa();
            userJpa.id = this.id;
            userJpa.login = this.login;
            userJpa.email = this.email;
            userJpa.password = this.password;
            userJpa.role = this.role;

            return userJpa;
        }
    }

}