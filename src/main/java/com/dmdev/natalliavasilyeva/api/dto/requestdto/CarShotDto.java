package com.dmdev.natalliavasilyeva.api.dto.requestdto;


import java.io.InputStream;

public class CarShotDto {

    private String brandName;
    private String category;
    private String color;
    private String yearOfProduction;
    private String number;
    private boolean isRepaired;
    private InputStream image;

    private String imageName;


    public CarShotDto(String brandName, String category, String color, String yearOfProduction, String number, boolean isRepaired, InputStream image, String imageName) {
        this.brandName = brandName;
        this.category = category;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
        this.number = number;
        this.isRepaired = isRepaired;
        this.image = image;
        this.imageName = imageName;
    }


    public String getColor() {
        return color;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public String getNumber() {
        return number;
    }


    public boolean isRepaired() {
        return isRepaired;
    }

    public InputStream getImage() {
        return image;
    }

    public String getImageName() {
        return imageName;
    }

    public String getCategory() {
        return category;
    }

    public String getBrandName() {
        return brandName;
    }
}