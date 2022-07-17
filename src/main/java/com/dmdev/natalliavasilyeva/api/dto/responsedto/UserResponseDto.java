package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import java.time.LocalDate;
import java.util.List;

public class UserResponseDto extends UserShotResponseDto {

    private List<OrderResponseDto> orders;

    public UserResponseDto(long id,
                           String email,
                           String name,
                           String surname,
                           String address,
                           String phone,
                           LocalDate birthday,
                           DriverLicenseDto driverLicenseDto,
                           List<OrderResponseDto> orders) {
        super(id, email, name, surname, address, phone, birthday, driverLicenseDto);
        this.orders = orders;
    }
}