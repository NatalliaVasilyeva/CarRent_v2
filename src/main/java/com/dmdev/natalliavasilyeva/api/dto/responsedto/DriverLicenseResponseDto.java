package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.time.LocalDateTime;

public class DriverLicenseResponseDto {

    private Long id;
    private String driverLicenseNumber;
    private LocalDateTime driverLicenseIssueDate;
    private LocalDateTime driverLicenseExpiredDate;

    public DriverLicenseResponseDto() {
    }
    public DriverLicenseResponseDto(Long id, String driverLicenseNumber, LocalDateTime driverLicenseIssueDate, LocalDateTime driverLicenseExpiredDate) {
        this.id = id;
        this.driverLicenseNumber = driverLicenseNumber;
        this.driverLicenseIssueDate = driverLicenseIssueDate;
        this.driverLicenseExpiredDate = driverLicenseExpiredDate;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }
    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }
    public LocalDateTime getDriverLicenseIssueDate() {
        return driverLicenseIssueDate;
    }
    public void setDriverLicenseIssueDate(LocalDateTime driverLicenseIssueDate) {
        this.driverLicenseIssueDate = driverLicenseIssueDate;
    }
    public LocalDateTime getDriverLicenseExpiredDate() {
        return driverLicenseExpiredDate;
    }
    public void setDriverLicenseExpiredDate(LocalDateTime driverLicenseExpiredDate) {
        this.driverLicenseExpiredDate = driverLicenseExpiredDate;
    }
}