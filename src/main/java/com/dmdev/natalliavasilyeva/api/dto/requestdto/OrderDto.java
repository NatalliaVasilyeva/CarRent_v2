package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;

import java.time.LocalDateTime;

public class OrderDto {

    private long carId;
    private LocalDateTime startRentalDate;
    private LocalDateTime endRentalDate;
    private long userId;
    private String passport;
    private boolean isInsuranceNeeded;
    private String orderStatus;

    public OrderDto(long carId, LocalDateTime startRentalDate, LocalDateTime endRentalDate, long userId, String passport, boolean isInsuranceNeeded) {
        this.carId = carId;
        this.startRentalDate = startRentalDate;
        this.endRentalDate = endRentalDate;
        this.userId = userId;
        this.passport = passport;
        this.isInsuranceNeeded = isInsuranceNeeded;
        this.orderStatus = OrderStatus.CONFIRMATION_WAIT.name();
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