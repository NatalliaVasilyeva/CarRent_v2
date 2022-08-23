package com.dmdev.natalliavasilyeva.api.dto.requestdto;


import java.io.InputStream;

public class CarDto extends CarShotDto {

    private String modelName;
    private String  transmission;
    private String engineType;
    private String vin;

    public CarDto(String brandName, String modelName, String transmission, String engineType, String category, String color, String yearOfProduction, String number, String vin, boolean isRepaired, InputStream image, String imageName) {
       super(brandName, category, color,yearOfProduction, number, isRepaired,image, imageName);
        this.modelName = modelName;
        this.transmission = transmission;
        this.engineType = engineType;
        this.vin = vin;
    }

    public String getModelName() {
        return modelName;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getEngineType() {
        return engineType;
    }

    public String getVin() {
        return vin;
    }

}