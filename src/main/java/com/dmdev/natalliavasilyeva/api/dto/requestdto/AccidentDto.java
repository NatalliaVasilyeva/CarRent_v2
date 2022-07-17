package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccidentDto {

    private long orderId;
    private LocalDateTime accidentDate;
    private String description;
    private BigDecimal damage;

    public AccidentDto(long orderId, LocalDateTime accidentDate, String description, BigDecimal damage) {
        this.orderId = orderId;
        this.accidentDate = accidentDate;
        this.description = description;
        this.damage = damage;
    }

    public long getOrderId() {
        return orderId;
    }

    public LocalDateTime getAccidentDate() {
        return accidentDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getDamage() {
        return damage;
    }
}