package com.dmdev.natalliavasilyeva.api.mapper;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.UserDto;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.UserLoginDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.UserResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.UserShotResponseDto;
import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicenseJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserDetailsJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.domain.model.DriverLicense;
import com.dmdev.natalliavasilyeva.domain.model.Role;
import com.dmdev.natalliavasilyeva.domain.model.ShotUser;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.domain.model.UserLogin;
import com.dmdev.natalliavasilyeva.utils.Converter;
import com.dmdev.natalliavasilyeva.utils.DateTimeService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public final class UserMapper {

    private UserMapper() {
    }

    public static UserShotResponseDto toShotDto(User user) {
        return new UserShotResponseDto(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getAddress(),
                user.getPhone(),
                user.getRole(),
                LocalDate.ofInstant(user.getBirthday(), ZoneOffset.UTC),
                DriverLicenseMapper.toDto(user.getDriverLicense())
        );
    }

    public static UserShotResponseDto toShotDtoFromShotUser(ShotUser user) {
        return new UserShotResponseDto(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getRole()
        );
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getAddress(),
                user.getPhone(),
                user.getRole(),
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
                .birthday(DateTimeService.fromLocalDateTimeDateToInstant(userDto.getBirthday()))
                .registrationDate(Instant.now())
                .driverLicense(new DriverLicense.Builder()
                        .number(userDto.getDriverLicenseNumber())
                        .issueDate(DateTimeService.fromLocalDateTimeDateToInstant(userDto.getDriverLicenseIssueDate()))
                        .expiredDate(DateTimeService.fromLocalDateTimeDateToInstant(userDto.getDriverLicenseExpiredDate()))
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
        var builder = new DriverLicense.Builder();
        Optional.ofNullable(driverLicenseJpa.getNumber()).ifPresent(builder::number);
        Optional.ofNullable(driverLicenseJpa.getIssueDate()).ifPresent(builder::issueDate);
        Optional.ofNullable(driverLicenseJpa.getExpiredDate()).ifPresent(builder::expiredDate);
        DriverLicense driverLicense = builder.build();
        return new User.Builder()
                .userId(userJpa.getId())
                .login(userJpa.getLogin())
                .email(userJpa.getEmail())
                .role(Role.getEnum(userJpa.getRole()))
                .userDetailsId(userDetailsJpa.getId())
                .name(userDetailsJpa.getName())
                .surname(userDetailsJpa.getSurname())
                .address(userDetailsJpa.getAddress())
                .phone(userDetailsJpa.getPhone())
                .birthday(userDetailsJpa.getBirthday())
                .registrationDate(userDetailsJpa.getRegistrationDate())
                .driverLicense(driverLicense)
                .build();
    }


    public static ShotUser fromJpa(UserJpa userJpa) {
        return new ShotUser.Builder()
                .userId(userJpa.getId())
                .login(userJpa.getLogin())
                .email(userJpa.getEmail())
                .role(Role.getEnum(userJpa.getRole()))
                .build();
    }

    public static List<ShotUser> fromJpaList(List<UserJpa> userJpas) {
        return userJpas.isEmpty() ? Collections.emptyList() : userJpas.stream().map(UserMapper::fromJpa).collect(Collectors.toList());
    }

    public static List<User> fromJpaList(List<UserJpa> userJpas, List<UserDetailsJpa> userDetailsJpas, List<DriverLicenseJpa> driverLicenseJpas) {
        if (userJpas.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<User> users = mergeUserAndUserDetailsJpaLists(userJpas, userDetailsJpas);
            return mergeUserWithDriverLicenseLists(users, driverLicenseJpas);
        }
    }


    public static UserDetailsJpa toUserDetailsJpa(User user) {
        var builder = new UserDetailsJpa.Builder();
        Optional.ofNullable(user.getUserDetailsId()).ifPresent(builder::id);
        Optional.ofNullable(user.getId()).ifPresent(builder::user);
        if (user.getRegistrationDate() != null) {
            builder.registrationDate(user.getRegistrationDate());
        } else {
            builder.registrationDate(Instant.now());
        }
        builder
//                .user(user.getId())
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
//                .password(user.getPassword())
                .role(user.getRole() == null ? Role.CLIENT.name() : user.getRole().name());
        return builder.build();
    }

    public static DriverLicenseJpa toDriverLicenseJpa(User user) {
        var builder = new DriverLicenseJpa.Builder();
        Optional.ofNullable(user.getDriverLicense().getId()).ifPresent(builder::id);
        Optional.ofNullable(user.getUserDetailsId()).ifPresent(builder::user);
        builder
//                .user(user.getUserDetailsId())
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
                            builder.role(Role.getEnum(userJpaEntry.getValue().getRole()));
                            userDetailsJpas.stream().filter(u -> u.getUserId() == userJpaEntry.getKey())
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