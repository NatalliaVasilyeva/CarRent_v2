package com.dmdev.natalliavasilyeva.domain.model;


import java.util.Objects;

public class Category implements Identifiable {

    private Long id;
    private String name;
    private Price price;

    public Category() {
    }

    private Category(Long id, String name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(price, category.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public static final class Builder {

        private Long id;
        private String name;

        private Price price;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(Price price) {
            this.price = price;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.id = this.id;
            category.name = this.name;
            category.price = this.price;

            return category;
        }
    }
}