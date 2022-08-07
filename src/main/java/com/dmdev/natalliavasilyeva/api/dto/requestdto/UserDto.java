package com.dmdev.natalliavasilyeva.api.dto.requestdto;

import java.time.LocalDateTime;

public class UserDto {

    private String email;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private LocalDateTime birthday;
    private String driverLicenseNumber;
    private LocalDateTime driverLicenseIssueDate;
    private LocalDateTime driverLicenseExpiredDate;

    public UserDto(String email,
                   String login,
                   String password,
                   String name,
                   String surname,
                   String address,
                   String phone,
                   LocalDateTime birthday,
                   String driverLicenseNumber,
                   LocalDateTime driverLicenseIssueDate,
                   LocalDateTime driverLicenseExpiredDate) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.driverLicenseNumber = driverLicenseNumber;
        this.driverLicenseIssueDate = driverLicenseIssueDate;
        this.driverLicenseExpiredDate = driverLicenseExpiredDate;
    }


    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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

    public LocalDateTime getBirthday() {
        return birthday;
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