package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.math.BigDecimal;

public class ModelResponseDTO {

    private Long id;
    private String name;
    private String brandName;
    private String transmission;
    private String engineType;
    private String category;
    private BigDecimal price;

    public ModelResponseDTO(Long id, String name, String brandName, String transmission, String engineType, String category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.transmission = transmission;
        this.engineType = engineType;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getBrandName() {
        return brandName;
    }
    public String getTransmission() {
        return transmission;
    }
    public String getEngineType() {
        return engineType;
    }
    public String getCategory() {
        return category;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Long getId() {
        return id;
    }
}