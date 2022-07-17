package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import java.time.LocalDateTime;

public class OrderCreateDto {

    private static final String DEFAULT_ORDER_STATUS = "CONFIRMATION_WAIT";

    private long carId;
    private LocalDateTime startRentalDate;
    private LocalDateTime endRentalDate;
    private long userId;
    private String passport;
    private boolean isInsuranceNeeded;
    private String orderStatus;

    public OrderCreateDto(long carId, LocalDateTime startRentalDate, LocalDateTime endRentalDate, long userId, String passport, boolean isInsuranceNeeded) {
        this.carId = carId;
        this.startRentalDate = startRentalDate;
        this.endRentalDate = endRentalDate;
        this.userId = userId;
        this.passport = passport;
        this.isInsuranceNeeded = isInsuranceNeeded;
        this.orderStatus = DEFAULT_ORDER_STATUS;
    }

    public long getCarId() {
        return carId;
    }

    public LocalDateTime getStartRentalDate() {
        return startRentalDate;
    }

    public LocalDateTime getEndRentalDate() {
        return endRentalDate;
    }

    public long getUserId() {
        return userId;
    }

    public String getPassport() {
        return passport;
    }

    public boolean isInsuranceNeeded() {
        return isInsuranceNeeded;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}