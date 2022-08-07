package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import java.time.LocalDateTime;

public class DriverLicenseDto {

    private Long userDetailsId;
    private String driverLicenseNumber;
    private LocalDateTime driverLicenseIssueDate;
    private LocalDateTime driverLicenseExpiredDate;

    public DriverLicenseDto(Long userDetailsId, String driverLicenseNumber, LocalDateTime driverLicenseIssueDate, LocalDateTime driverLicenseExpiredDate) {
        this.userDetailsId = userDetailsId;
        this.driverLicenseNumber = driverLicenseNumber;
        this.driverLicenseIssueDate = driverLicenseIssueDate;
        this.driverLicenseExpiredDate = driverLicenseExpiredDate;
    }

    public Long getUserDetailsId() {
        return userDetailsId;
    }
    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }
    public LocalDateTime getDriverLicenseIssueDate() {
        return driverLicenseIssueDate;
    }
    public LocalDateTime getDriverLicenseExpiredDate() {
        return driverLicenseExpiredDate;
    }
}