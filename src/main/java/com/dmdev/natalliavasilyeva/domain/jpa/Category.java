package com.dmdev.natalliavasilyeva.domain.jpa;

public class Category implements Entity {

    private long id;
    private String name;
    private long priceId;

    public Category() {
    }

    private Category(long id, String name, long priceId) {
        this.id = id;
        this.name = name;
        this.priceId = priceId;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPriceId() {
        return priceId;
    }

    public static final class Builder {

        private long id;
        private String name;

        private long priceId;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(long priceId) {
            this.priceId = priceId;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.id = this.id;
            category.name = this.name;
            category.priceId = this.priceId;

            return category;
        }
    }
}