package com.dmdev.natalliavasilyeva.domain.model;

import javax.servlet.http.Part;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Car implements Identifiable {
    private Long id;
    private Model model;
    private Color color;
    private String yearOfProduction;
    private String number;
    private String vin;
    private boolean isRepaired;
    private String image;

    private Part imageContent;
    private List<Accident> accidents;


    public Car() {
        // empty default
    }

    private Car(
            Long id,
            Model model,
            Color color,
            String yearOfProduction,
            String number,
            String vin,
            boolean isRepaired,
            String image,
            List<Accident> accidents) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.number = number;
        this.vin = vin;
        this.isRepaired = isRepaired;
        this.image = image;
        this.accidents = accidents;
    }

    public Long getId() {
        return id;
    }

    public Model getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public String getNumber() {
        return number;
    }

    public String getVin() {
        return vin;
    }

    public boolean isRepaired() {
        return isRepaired;
    }

    public String getImage() {
        return image;
    }

    public Part getImageContent() {
        return imageContent;
    }
    public List<Accident> getAccidents() {
        return accidents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return isRepaired == car.isRepaired && Objects.equals(id, car.id) && Objects.equals(model, car.model) && color == car.color && Objects.equals(yearOfProduction, car.yearOfProduction) && Objects.equals(number, car.number) && Objects.equals(vin, car.vin) && Objects.equals(image, car.image) && Objects.equals(accidents, car.accidents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, color, yearOfProduction, number, vin, isRepaired, image, accidents);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model=" + model +
                ", color=" + color +
                ", yearOfProduction='" + yearOfProduction + '\'' +
                ", number='" + number + '\'' +
                ", vin='" + vin + '\'' +
                ", isRepaired=" + isRepaired +
                ", image='" + image + '\'' +
                ", accidents=" + accidents +
                '}';
    }

    public static final class Builder {

        private Long id;
        private Model model;
        private Color color;
        private String yearOfProduction;
        private String number;
        private String vin;
        private boolean isRepaired;
        private String image;

        private Part imageContent;

        private List<Accident> accidents;

        public Builder() {
            this.accidents = Collections.emptyList();
            this.isRepaired = false;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder model(Model model) {
            this.model = model;
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

        public Builder content(Part imageContent) {
            this.imageContent = imageContent;
            return this;
        }

        public Builder accidents(List<Accident> accidents) {
            this.accidents = accidents;
            return this;
        }

        public Car build() {
            Car car = new Car();
            car.id = this.id;
            car.model = this.model;
            car.color = this.color;
            car.yearOfProduction = this.yearOfProduction;
            car.number = this.number;
            car.vin = this.vin;
            car.isRepaired = this.isRepaired;
            car.image = this.image;
            car.imageContent = this.imageContent;
            car.accidents = this.accidents;
            return car;
        }
    }
}