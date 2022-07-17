package com.dmdev.natalliavasilyeva.domain.jpa;

import java.io.Serializable;

public class BrandJpa implements Entity, Serializable {
    private long id;
    private String name;

    public BrandJpa() {
    }

    private BrandJpa(long id, String name) {
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

    public void setName(String name) {
        this.name = name;
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

        public BrandJpa build() {
            BrandJpa brandJpa = new BrandJpa();
            brandJpa.id = this.id;
            brandJpa.name = this.name;

            return brandJpa;
        }
    }

}