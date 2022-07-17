package com.dmdev.natalliavasilyeva.api.mapper;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.UserDto;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.UserLoginDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.DriverLicenseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.UserResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.UserShotResponseDto;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicenseJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserDetailsJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.domain.model.DriverLicense;
import com.dmdev.natalliavasilyeva.domain.model.Role;
import com.dmdev.natalliavasilyeva.domain.model.ShotUser;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.domain.model.UserLogin;
import com.dmdev.natalliavasilyeva.utils.Converter;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserMapper {

    public static UserShotResponseDto toShotDto(User user) {
        return new UserShotResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getAddress(),
                user.getPhone(),
                LocalDate.ofInstant(user.getBirthday(), ZoneOffset.UTC),
                DriverLicenseMapper.toDto(user.getDriverLicense())
        );
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getAddress(),
                user.getPhone(),
                LocalDate.ofInstant(user.getBirthday(), ZoneOffset.UTC),
                DriverLicenseMapper.toDto(user.getDriverLicense()),
                OrderMapper.toDtos(user.getOrders())
        );
    }

    public static List<UserResponseDto> toDtos(List<User> users) {
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public static List<UserShotResponseDto> toShotDtos(List<User> users) {
        return users.stream().map(UserMapper::toShotDto).collect(Collectors.toList());
    }


    public static User fromDto(UserDto userDto) {
        return new User.Builder()
                .login(userDto.getLogin())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(Role.CLIENT)
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .address(userDto.getAddress())
                .phone(userDto.getPhone())
                .birthday(userDto.getBirthday().toInstant(ZoneOffset.UTC))
                .registrationDate(Instant.now())
                .driverLicense(new DriverLicense.Builder()
                        .number(userDto.getDriverLicenseNumber())
                        .issueDate(userDto.getDriverLicenseIssueDate().toInstant(ZoneOffset.UTC))
                        .expiredDate(userDto.getDriverLicenseExpiredDate().toInstant(ZoneOffset.UTC))
                        .build())
                .build();
    }

    public static UserLogin fromDto(UserLoginDto userLoginDto) {
        return new UserLogin.Builder()
                .login(userLoginDto.getLogin())
                .password(userLoginDto.getPassword())
                .build();
    }

    public static User fromJpa(UserJpa userJpa, UserDetailsJpa userDetailsJpa, DriverLicenseJpa driverLicenseJpa) {
        return new User.Builder()
                .userId(userJpa.getId())
                .login(userJpa.getLogin())
                .email(userJpa.getEmail())
                .role(Role.valueOf(userJpa.getRole()))
                .userDetailsId(userDetailsJpa.getId())
                .name(userDetailsJpa.getName())
                .surname(userDetailsJpa.getSurname())
                .address(userDetailsJpa.getAddress())
                .phone(userDetailsJpa.getPhone())
                .birthday(userDetailsJpa.getBirthday())
                .registrationDate(userDetailsJpa.getRegistrationDate())
                .driverLicense(new DriverLicense.Builder()
                        .number(driverLicenseJpa.getNumber())
                        .issueDate(driverLicenseJpa.getIssueDate())
                        .expiredDate(driverLicenseJpa.getExpiredDate())
                        .build())
                .build();
    }


    public static ShotUser fromJpa(UserJpa userJpa) {
        return new ShotUser.Builder()
                .userId(userJpa.getId())
                .login(userJpa.getLogin())
                .email(userJpa.getEmail())
                .role(Role.valueOf(userJpa.getRole()))
                .build();
    }

    public static List<ShotUser> fromJpaList(List<UserJpa> userJpas) {
        return userJpas.size() == 0 ? Collections.emptyList() : userJpas.stream().map(UserMapper::fromJpa).collect(Collectors.toList());
    }

    public static List<User> fromJpaList(List<UserJpa> userJpas, List<UserDetailsJpa> userDetailsJpas, List<DriverLicenseJpa> driverLicenseJpas) {
        if (userJpas.size() == 0) {
            return Collections.emptyList();
        } else {
            List<User> users = mergeUserAndUserDetailsJpaLists(userJpas, userDetailsJpas);
            return mergeUserWithDriverLicenseLists(users, driverLicenseJpas);
        }
    }


    public static UserDetailsJpa toUserDetailsJpa(User user) {
        var builder = new UserDetailsJpa.Builder();
        Optional.ofNullable(user.getUserDetailsId()).ifPresent(builder::id);
        Optional.ofNullable(user.getRegistrationDate())
                .map(date -> builder.registrationDate(date))
                .orElse(builder.registrationDate(Instant.now()));
        builder
                .user(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .address(user.getAddress())
                .phone(user.getPhone())
                .birthday(user.getBirthday());
        return builder.build();
    }

    public static UserJpa toUserJpa(User user) {
        var builder = new UserJpa.Builder();
        Optional.ofNullable(user.getId()).ifPresent(builder::id);
        builder
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole() == null ? Role.CLIENT.name() : user.getRole().name());
        return builder.build();
    }

    public static DriverLicenseJpa toDriverLicenseJpa(User user) {
        var builder = new DriverLicenseJpa.Builder();
        Optional.ofNullable(user.getDriverLicense().getId()).ifPresent(builder::id);
        builder
                .user(user.getUserDetailsId())
                .number(user.getDriverLicense().getNumber())
                .issueDate(user.getDriverLicense().getIssueDate())
                .expiredDate(user.getDriverLicense().getExpiredDate());
        return builder.build();
    }

    private static List<User> mergeUserAndUserDetailsJpaLists(List<UserJpa> userJpas, List<UserDetailsJpa> userDetailsJpas) {
        return new ArrayList<>(Converter.convertUserJpaListToMap(userJpas)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        userJpaEntry -> {
                            User.Builder builder = new User
                                    .Builder();
                            builder.userId(userJpaEntry.getValue().getId());
                            builder.login(userJpaEntry.getValue().getLogin());
                            builder.email(userJpaEntry.getValue().getEmail());
                            builder.role(Role.valueOf(userJpaEntry.getValue().getRole()));
                            userDetailsJpas.stream().filter(u -> u.getUser() == userJpaEntry.getKey())
                                    .forEach(ud -> {
                                        builder.userDetailsId(ud.getId());
                                        builder.name(ud.getName());
                                        builder.surname(ud.getSurname());
                                        builder.address(ud.getAddress());
                                        builder.phone(ud.getPhone());
                                        builder.birthday(ud.getBirthday());
                                        builder.registrationDate(ud.getRegistrationDate());
                                    });
                            return builder
                                    .build();
                        }
                ))
                .values());
    }

    private static List<User> mergeUserWithDriverLicenseLists(List<User> users, List<DriverLicenseJpa> driverLicenseDtos) {
        List<User> newUsersList = users;
        return new ArrayList<>(Converter.convertUserListToMapByUserDetailsId(newUsersList)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        userEntry -> {
                            Optional<DriverLicenseJpa> optional = driverLicenseDtos.stream().filter(dl -> dl.getUserDetailsId() == userEntry.getKey())
                                    .findFirst();

                            User user = userEntry.getValue();
                            if (optional.isEmpty()) {
                                user.setDriverLicense(null);
                            } else {
                                DriverLicenseJpa dl = optional.get();
                                user.setDriverLicense(
                                        new DriverLicense.Builder()
                                                .number(dl.getNumber())
                                                .issueDate(dl.getIssueDate())
                                                .expiredDate(dl.getExpiredDate())
                                                .build());
                            }
                            return user;
                        }
                ))
                .values());
    }
}