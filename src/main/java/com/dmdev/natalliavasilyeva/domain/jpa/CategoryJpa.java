package com.dmdev.natalliavasilyeva.domain.jpa;

public class CategoryJpa implements Entity {

    private long id;
    private String name;
    private long priceId;

    public CategoryJpa() {
    }

    private CategoryJpa(long id, String name, long priceId) {
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

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPriceId(long priceId) {
        this.priceId = priceId;
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

        public CategoryJpa build() {
            CategoryJpa categoryJpa = new CategoryJpa();
            categoryJpa.id = this.id;
            categoryJpa.name = this.name;
            categoryJpa.priceId = this.priceId;

            return categoryJpa;
        }
    }
}