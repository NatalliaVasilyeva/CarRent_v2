package com.dmdev.natalliavasilyeva.api.dto.responsedto;

import com.dmdev.natalliavasilyeva.domain.model.Role;

import java.time.LocalDate;
import java.util.List;

public class UserResponseDto extends UserShotResponseDto {

    private List<OrderResponseDto> orders;

    public UserResponseDto(long id,
                           String login,
                           String email,
                           String name,
                           String surname,
                           String address,
                           String phone,
                           Role role,
                           LocalDate birthday,
                           DriverLicenseResponseDto driverLicenseDto,
                           List<OrderResponseDto> orders) {
        super(id, login, email, name, surname, address, phone, role, birthday, driverLicenseDto);
        this.orders = orders;
    }
}