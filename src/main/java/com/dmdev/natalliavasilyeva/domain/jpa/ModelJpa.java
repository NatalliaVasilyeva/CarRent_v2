package com.dmdev.natalliavasilyeva.domain.jpa;


import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;

import java.io.Serializable;

public class ModelJpa implements Entity, Serializable {

    private long id;
    private long brandId;
    private long categoryId;
    private String name;
    private Transmission transmission;
    private EngineType engineType;


    public ModelJpa() {
    }

    public ModelJpa(long id, long brandId, long categoryId, String name, Transmission transmission, EngineType engineType) {
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

    public void setId(long id) {
        this.id = id;
    }
    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }
    public static final class Builder {
        private long id;
        private long brandId;
        private long categoryId;

        private String name;

        private Transmission transmission;

        private EngineType engineType;

        public Builder id(Long id) {
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

        public ModelJpa build() {
            ModelJpa modelJpa = new ModelJpa();
            modelJpa.id = this.id;
            modelJpa.brandId = this.brandId;
            modelJpa.categoryId = this.categoryId;
            modelJpa.name = this.name;
            modelJpa.transmission = this.transmission;
            modelJpa.engineType = this.engineType;

            return modelJpa;
        }
    }
}