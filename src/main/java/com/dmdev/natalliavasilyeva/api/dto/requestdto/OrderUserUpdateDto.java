package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import java.time.LocalDateTime;

public class OrderUserUpdateDto {

    private LocalDateTime startRentalDate;
    private LocalDateTime endRentalDate;
    private boolean insuranceNeeded;

    public OrderUserUpdateDto(LocalDateTime startRentalDate, LocalDateTime endRentalDate, boolean insuranceNeeded) {
        this.startRentalDate = startRentalDate;
        this.endRentalDate = endRentalDate;
        this.insuranceNeeded = insuranceNeeded;
    }

    public LocalDateTime getStartRentalDate() {
        return startRentalDate;
    }
    public LocalDateTime getEndRentalDate() {
        return endRentalDate;
    }
    public boolean isInsuranceNeeded() {
        return insuranceNeeded;
    }
}