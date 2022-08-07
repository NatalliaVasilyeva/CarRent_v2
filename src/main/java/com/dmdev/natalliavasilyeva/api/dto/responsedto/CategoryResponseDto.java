package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.math.BigDecimal;

public class CategoryResponseDto {

    private String name;

    private BigDecimal pricePerDay;

    public CategoryResponseDto(String name, BigDecimal pricePerDay) {
        this.name = name;
        this.pricePerDay = pricePerDay;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }
    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}