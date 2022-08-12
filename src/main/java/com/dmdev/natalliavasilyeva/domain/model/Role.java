package com.dmdev.natalliavasilyeva.domain.model;

public enum Role {
    CLIENT("client"),
    ADMIN("admin");

    Role(String role) {
        this.role = role;
    }

    String role;

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return this.getRole();
    }

    public static Role getEnum(String value) {
        for(Role v : values())
            if(v.getRole().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}