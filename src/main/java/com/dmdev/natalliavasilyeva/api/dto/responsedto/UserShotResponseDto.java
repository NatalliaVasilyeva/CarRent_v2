package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import com.dmdev.natalliavasilyeva.domain.model.Role;

import java.time.LocalDate;

public class UserShotResponseDto {
    private long id;
    private String login;
    private String email;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private LocalDate birthday;
    private Role role;
    private DriverLicenseResponseDto driverLicenseDto;

    public UserShotResponseDto(long id,
                               String login,
                               String email,
                               String name,
                               String surname,
                               String address,
                               String phone,
                               Role role,
                               LocalDate birthday,
                               DriverLicenseResponseDto driverLicenseDto) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.birthday = birthday;
        this.driverLicenseDto = driverLicenseDto;
    }

    public UserShotResponseDto(long id,
                               String login,
                               String email,
                               Role role
                               ) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.role = role;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public DriverLicenseResponseDto getDriverLicenseDto() {
        return driverLicenseDto;
    }
    public void setDriverLicenseDto(DriverLicenseResponseDto driverLicenseDto) {
        this.driverLicenseDto = driverLicenseDto;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
}