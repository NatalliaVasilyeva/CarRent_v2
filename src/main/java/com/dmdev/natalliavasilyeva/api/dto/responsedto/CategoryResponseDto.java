package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.math.BigDecimal;

public class CategoryResponseDto {

    private String name;

    private BigDecimal pricePerDay;

    public CategoryResponseDto(String name, BigDecimal pricePerDay) {
        this.name = name;
        this.pricePerDay = pricePerDay;
    }
}