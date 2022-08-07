package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponseDto extends OrderShotResponseDto {

    private String carDescription;
    private LocalDateTime startRentalDate;
    private LocalDateTime endRentalDate;
    private boolean insuranceNeeded;
    private BigDecimal sum;
    private boolean accidentExist;

    public OrderResponseDto(long id, LocalDateTime date, String orderStatus, String carDescription, LocalDateTime startRentalDate, LocalDateTime endRentalDate, boolean insuranceNeeded, BigDecimal sum, boolean accidentExist) {
        super(id, date, orderStatus);
        this.carDescription = carDescription;
        this.startRentalDate = startRentalDate;
        this.endRentalDate = endRentalDate;
        this.insuranceNeeded = insuranceNeeded;
        this.sum = sum;
        this.accidentExist = accidentExist;
    }

    public String getCarDescription() {
        return carDescription;
    }
    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }
    public LocalDateTime getStartRentalDate() {
        return startRentalDate;
    }
    public void setStartRentalDate(LocalDateTime startRentalDate) {
        this.startRentalDate = startRentalDate;
    }
    public LocalDateTime getEndRentalDate() {
        return endRentalDate;
    }
    public void setEndRentalDate(LocalDateTime endRentalDate) {
        this.endRentalDate = endRentalDate;
    }
    public boolean isInsuranceNeeded() {
        return insuranceNeeded;
    }
    public void setInsuranceNeeded(boolean insuranceNeeded) {
        insuranceNeeded = insuranceNeeded;
    }
    public BigDecimal getSum() {
        return sum;
    }
    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
    public boolean isAccidentExist() {
        return accidentExist;
    }
    public void setAccidentExist(boolean accidentExist) {
        this.accidentExist = accidentExist;
    }

    @Override
    public String toString() {
        return "OrderResponseDto{" +
                "carDescription='" + carDescription + '\'' +
                ", startRentalDate=" + startRentalDate +
                ", endRentalDate=" + endRentalDate +
                ", insuranceNeeded=" + insuranceNeeded +
                ", sum=" + sum +
                ", accidentExist=" + accidentExist +
                '}';
    }
}