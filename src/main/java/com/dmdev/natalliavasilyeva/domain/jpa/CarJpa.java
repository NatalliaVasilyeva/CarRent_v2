package com.dmdev.natalliavasilyeva.domain.jpa;

import com.dmdev.natalliavasilyeva.domain.model.Color;

import java.io.Serializable;

public class CarJpa implements Entity, Serializable {
    private long id;
    private long modelId;
    private Color color;
    private String yearOfProduction;
    private String number;
    private String vin;
    private boolean isRepaired;
    private String image;

    public CarJpa() {
        // empty default
    }

    private CarJpa(
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

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }
    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
    public void setVin(String vin) {
        this.vin = vin;
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

        public CarJpa build() {
            CarJpa carJpa = new CarJpa();
            carJpa.id = this.id;
            carJpa.modelId = this.modelId;
            carJpa.color = this.color;
            carJpa.yearOfProduction = this.yearOfProduction;
            carJpa.number = this.number;
            carJpa.vin = this.vin;
            carJpa.isRepaired = this.isRepaired;
            carJpa.image = this.image;
            return carJpa;
        }
    }
}