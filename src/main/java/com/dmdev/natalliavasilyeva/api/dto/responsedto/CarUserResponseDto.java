package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.math.BigDecimal;

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
                ", pricePerDay=" + pricePerDay +
                '}';
    }
}