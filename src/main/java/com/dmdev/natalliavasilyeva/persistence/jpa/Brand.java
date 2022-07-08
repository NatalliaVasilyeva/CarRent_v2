package com.dmdev.natalliavasilyeva.persistence.jpa;

import java.io.Serializable;

public class Brand implements Entity, Serializable {
    private long id;
    private String name;

    public Brand() {
    }

    private Brand(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static final class Builder {

        private long id;
        private String name;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Brand build() {
            Brand brand = new Brand();
            brand.id = this.id;
            brand.name = this.name;

            return brand;
        }
    }

}