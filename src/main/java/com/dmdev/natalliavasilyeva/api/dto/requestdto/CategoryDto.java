package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import java.math.BigDecimal;

public class CategoryDto {

    private String name;

    private long priceId;

    private BigDecimal priceSum;

    public CategoryDto(String name, long priceId) {
        this.name = name;
        this.priceId = priceId;
    }

    public CategoryDto(String name, BigDecimal priceSum) {
        this.name = name;
        this.priceSum = priceSum;
    }

    public String getName() {
        return name;
    }

    public long getPriceId() {
        return priceId;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }
}