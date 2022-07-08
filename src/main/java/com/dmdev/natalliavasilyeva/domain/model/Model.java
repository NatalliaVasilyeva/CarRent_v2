package com.dmdev.natalliavasilyeva.domain.model;

import java.util.Objects;

public class Model implements Identifiable {

    private Long id;
    private String name;
    private Brand brand;
    private Transmission transmission;
    private EngineType engineType;
    private Category category;

    public Model() {
    }

    private Model(Long id, String name, Brand brand, Transmission transmission, EngineType engineType, Category category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.transmission = transmission;
        this.engineType = engineType;
        this.category = category;
    }

    public Long getId() {
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

    public Brand getBrand() {
        return brand;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(id, model.id) && Objects.equals(name, model.name) && Objects.equals(brand, model.brand) && transmission == model.transmission && engineType == model.engineType && Objects.equals(category, model.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, transmission, engineType, category);
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", transmission=" + transmission +
                ", engineType=" + engineType +
                ", category=" + category +
                '}';
    }

    public static final class Builder {

        private Long id;
        private String name;
        private Brand brand;

        private Transmission transmission;

        private EngineType engineType;

        private Category category;

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

        public Builder brand(Brand brand) {
            this.brand = brand;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Model build() {
            Model model = new Model();
            model.id = this.id;
            model.name = this.name;
            model.transmission = this.transmission;
            model.engineType = this.engineType;
            model.brand = this.brand;
            model.category = this.category;

            return model;
        }
    }
}