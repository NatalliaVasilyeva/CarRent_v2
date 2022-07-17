package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.time.LocalDate;

public class UserShotResponseDto {
    private long id;
    private String email;
    private String name;
    private String surname;
    private String address;
    private String phone;
    private LocalDate birthday;
    private DriverLicenseDto driverLicenseDto;

    public UserShotResponseDto(long id,
                               String email,
                               String name,
                               String surname,
                               String address,
                               String phone,
                               LocalDate birthday,
                               DriverLicenseDto driverLicenseDto) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.birthday = birthday;
        this.driverLicenseDto = driverLicenseDto;
    }
}