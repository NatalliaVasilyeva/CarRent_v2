package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.time.LocalDateTime;

public class DriverLicenseDto {

    private String driverLicenseNumber;
    private LocalDateTime driverLicenseIssueDate;
    private LocalDateTime driverLicenseExpiredDate;

    public DriverLicenseDto(String driverLicenseNumber, LocalDateTime driverLicenseIssueDate, LocalDateTime driverLicenseExpiredDate) {
        this.driverLicenseNumber = driverLicenseNumber;
        this.driverLicenseIssueDate = driverLicenseIssueDate;
        this.driverLicenseExpiredDate = driverLicenseExpiredDate;
    }
}