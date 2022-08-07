package com.dmdev.natalliavasilyeva.api.dto.responsedto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccidentResponseDto {

    private Long id;
    private LocalDateTime date;
    private String carDescription;
    private String carNumber;
    private String userName;
    private String description;
    private BigDecimal damage;

    public AccidentResponseDto(Long id, LocalDateTime date, String carDescription, String carNumber, String userName, String description, BigDecimal damage) {
        this.id = id;
        this.date = date;
        this.carDescription = carDescription;
        this.carNumber = carNumber;
        this.userName = userName;
        this.description = description;
        this.damage = damage;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getCarDescription() {
        return carDescription;
    }
    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }
    public String getCarNumber() {
        return carNumber;
    }
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getDamage() {
        return damage;
    }
    public void setDamage(BigDecimal damage) {
        this.damage = damage;
    }
}