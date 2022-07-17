package com.dmdev.natalliavasilyeva.domain.model;


import java.util.List;
import java.util.Objects;

public class Brand implements Identifiable {

    private Long id;
    private String name;
    private List<Model> models;

    public Brand() {
        //default constructor
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Model> getModels() {
        return models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id) && Objects.equals(name, brand.name) && Objects.equals(models, brand.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, models);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", models=" + models +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String name;
        private List<Model> models;


        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder models(List<Model> models) {
            this.models = models;
            return this;
        }

        public Brand build() {
            Brand brand = new Brand();
            brand.id = this.id;
            brand.name = this.name;
            brand.models = this.models;

            return brand;
        }
    }

}