package com.dmdev.natalliavasilyeva.api.dto.requestdto;

public class ModelDto {

    private String name;
    private String transmission;
    private String engineType;
    private String brand;
    private String category;

    public ModelDto(String name, String transmission, String engineType, String brand, String category) {
        this.name = name;
        this.transmission = transmission;
        this.engineType = engineType;
        this.brand = brand;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getEngineType() {
        return engineType;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }


}