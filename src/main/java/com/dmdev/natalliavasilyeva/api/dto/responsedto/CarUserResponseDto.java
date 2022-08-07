package com.dmdev.natalliavasilyeva.api.dto.responsedto;


import java.math.BigDecimal;
import java.util.Objects;

public class CarUserResponseDto {
    private Long id;
    private String brand;
    //    private ModelResponseDTO model;
    private String model;

    private String transmission;
    private String engineType;
    private String color;
    private String yearOfProduction;
    private String image;

    private String imageContent;
    private BigDecimal pricePerDay;

//    public CarUserResponseDto(String brand,St ModelResponseDTO model, String color, String yearOfProduction, String image, BigDecimal pricePerDay) {
//        this.brand = brand;
//        this.model = model;
//        this.color = color;
//        this.yearOfProduction = yearOfProduction;
//        this.image = image;
//        this.pricePerDay = pricePerDay;
//    }


    public CarUserResponseDto(Long id, String brand, String model, String transmission, String engineType, String color, String yearOfProduction, String image, BigDecimal pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.transmission = transmission;
        this.engineType = engineType;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.image = image;
        this.pricePerDay = pricePerDay;
    }

    public CarUserResponseDto(Long id, String brand, String model, String image, BigDecimal price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.image = image;
        this.pricePerDay = price;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getTransmission() {
        return transmission;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    public String getEngineType() {
        return engineType;
    }
    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getYearOfProduction() {
        return yearOfProduction;
    }
    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImageContent() {
        return imageContent;
    }
    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }
    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarUserResponseDto that = (CarUserResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(brand, that.brand) && Objects.equals(model, that.model) && Objects.equals(transmission, that.transmission) && Objects.equals(engineType, that.engineType) && Objects.equals(color, that.color) && Objects.equals(yearOfProduction, that.yearOfProduction) && Objects.equals(image, that.image) && Objects.equals(imageContent, that.imageContent) && Objects.equals(pricePerDay, that.pricePerDay);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, transmission, engineType, color, yearOfProduction, image, imageContent, pricePerDay);
    }

    @Override
    public String toString() {
        return "CarUserResponseDto{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", transmission='" + transmission + '\'' +
                ", engineType='" + engineType + '\'' +
                ", color='" + color + '\'' +
                ", yearOfProduction='" + yearOfProduction + '\'' +
                ", image='" + image + '\'' +
                ", imageContent='" + imageContent + '\'' +
                ", pricePerDay=" + pricePerDay +
                '}';
    }
}