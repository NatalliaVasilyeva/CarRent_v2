package com.dmdev.natalliavasilyeva.persistence.jpa;


import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;

import java.io.Serializable;

public class Model implements Entity, Serializable {

    private long id;
    private long brandId;
    private long categoryId;
    private String name;
    private Transmission transmission;

    private EngineType engineType;


    public Model() {
    }

    public Model(long id,  long brandId, long categoryId, String name, Transmission transmission, EngineType engineType) {
        this.id = id;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.transmission = transmission;
        this.engineType = engineType;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public long getBrandId() {
        return brandId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public static final class Builder {
        private long id;
        private long brandId;

        private long categoryId;

        private String name;

        private Transmission transmission;

        private EngineType engineType;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder transmission(Transmission transmission) {
            this.transmission = transmission;
            return this;
        }

        public Builder engine(EngineType engineType) {
            this.engineType = engineType;
            return this;
        }

        public Builder brand(long brandId) {
            this.brandId = brandId;
            return this;
        }

        public Builder category(long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Model build() {
            Model model = new Model();
            model.id = this.id;
            model.brandId = this.brandId;
            model.categoryId = this.categoryId;
            model.name = this.name;
            model.transmission = this.transmission;
            model.engineType = this.engineType;

            return model;
        }
    }
}