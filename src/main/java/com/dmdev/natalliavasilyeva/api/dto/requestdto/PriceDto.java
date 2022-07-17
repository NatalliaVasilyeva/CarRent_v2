package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import java.math.BigDecimal;

public class PriceDto {

    private BigDecimal sum;

    public PriceDto(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getSum() {
        return sum;
    }
}