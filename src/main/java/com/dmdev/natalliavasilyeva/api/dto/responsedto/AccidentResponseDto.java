package com.dmdev.natalliavasilyeva.api.dto.responsedto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccidentResponseDto {

    private LocalDateTime date;
    private String carDescription;
    private String carNumber;
    private String userName;
    private String description;
    private BigDecimal damage;

    public AccidentResponseDto(LocalDateTime date, String carDescription, String carNumber, String userName, String description, BigDecimal damage) {
        this.date = date;
        this.carDescription = carDescription;
        this.carNumber = carNumber;
        this.userName = userName;
        this.description = description;
        this.damage = damage;
    }
}