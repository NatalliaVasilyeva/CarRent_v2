package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import java.time.LocalDateTime;

public class UserUpdateDto {

    private String email;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private String licenseNumber;
    private LocalDateTime issueDate;
    private LocalDateTime expiredDate;

    public UserUpdateDto(String email, String name, String surname, String address, String phone, String licenseNumber, LocalDateTime issueDate, LocalDateTime expiredDate) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.licenseNumber = licenseNumber;
        this.issueDate = issueDate;
        this.expiredDate = expiredDate;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }
}