package com.dmdev.natalliavasilyeva.domain.jpa;

import com.dmdev.natalliavasilyeva.domain.model.Color;

import java.io.Serializable;

public class Car implements Entity, Serializable {
    private long id;
    private long modelId;
    private Color color;
    private String yearOfProduction;
    private String number;
    private String vin;
    private boolean isRepaired;
    private String image;

    public Car() {
        // empty default
    }

    private Car(
            long id,
            long modelId,
            Color color,
            String yearOfProduction,
            String number,
            String vin,
            boolean isRepaired,
            String image) {
        this.id = id;
        this.modelId = modelId;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.number = number;
        this.vin = vin;
        this.isRepaired = isRepaired;
        this.image = image;
    }

    @Override
    public long getId() {
        return id;
    }

    public long getModelId() {
        return modelId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVin() {
        return vin;
    }

    public boolean isRepaired() {
        return isRepaired;
    }

    public void setRepaired(boolean repaired) {
        isRepaired = repaired;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", modelId=" + modelId +
                ", color=" + color +
                ", yearOfProduction='" + yearOfProduction + '\'' +
                ", number='" + number + '\'' +
                ", vin='" + vin + '\'' +
                ", isRepaired=" + isRepaired +
                ", image='" + image + '\'' +
                '}';
    }

    public static final class Builder {

        private long id;
        private long modelId;
        private Color color;
        private String yearOfProduction;
        private String number;
        private String vin;
        private boolean isRepaired;
        private String image;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder model(long modelId) {
            this.modelId = modelId;
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder year(String year) {
            this.yearOfProduction = year;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder vin(String vin) {
            this.vin = vin;
            return this;
        }

        public Builder repaired(boolean isRepaired) {
            this.isRepaired = isRepaired;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Car build() {
            Car car = new Car();
            car.id = this.id;
            car.modelId = this.modelId;
            car.color = this.color;
            car.yearOfProduction = this.yearOfProduction;
            car.number = this.number;
            car.vin = this.vin;
            car.isRepaired = this.isRepaired;
            car.image = this.image;
            return car;
        }
    }
}