package com.dmdev.natalliavasilyeva.api.mapper;


import com.dmdev.natalliavasilyeva.api.dto.requestdto.DriverLicenseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.DriverLicenseResponseDto;
import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicenseJpa;
import com.dmdev.natalliavasilyeva.domain.model.DriverLicense;
import com.dmdev.natalliavasilyeva.utils.DateTimeService;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DriverLicenseMapper {

    private DriverLicenseMapper() {
    }

    public static DriverLicenseResponseDto toDto(DriverLicense driverLicense) {
        if (driverLicense == null) {
            return new DriverLicenseResponseDto();
        }
        return new DriverLicenseResponseDto(
                driverLicense.getId(),
                driverLicense.getNumber(),
                DateTimeService.toLocalDateTimeDateFromInstant(driverLicense.getIssueDate()),
                DateTimeService.toLocalDateTimeDateFromInstant(driverLicense.getExpiredDate())
        );
    }

    public static List<DriverLicenseResponseDto> toDtos(List<DriverLicense> driverLicensesList) {
        return driverLicensesList.stream().map(DriverLicenseMapper::toDto).collect(Collectors.toList());
    }

    public static DriverLicense fromDto(DriverLicenseDto driverLicenseDto) {
        return new DriverLicense.Builder()
                .user(driverLicenseDto.getUserDetailsId())
                .number(driverLicenseDto.getDriverLicenseNumber())
                .issueDate(driverLicenseDto.getDriverLicenseIssueDate().toInstant(ZoneOffset.UTC))
                .expiredDate(driverLicenseDto.getDriverLicenseExpiredDate().toInstant(ZoneOffset.UTC))
                .build();
    }

    public static DriverLicense fromJpa(DriverLicenseJpa driverLicenseJpa) {
        return new DriverLicense.Builder()
                .id(driverLicenseJpa.getId())
                .user(driverLicenseJpa.getUserDetailsId())
                .number(driverLicenseJpa.getNumber())
                .issueDate(driverLicenseJpa.getIssueDate())
                .expiredDate(driverLicenseJpa.getExpiredDate())
                .build();
    }

    public static List<DriverLicense> fromJpaList(List<DriverLicenseJpa> jpaCars) {
        return jpaCars.isEmpty() ? Collections.emptyList() : jpaCars.stream().map(DriverLicenseMapper::fromJpa).collect(Collectors.toList());
    }

    public static DriverLicenseJpa toJpa(DriverLicense driverLicense) {
        var builder = new DriverLicenseJpa.Builder();
        Optional.ofNullable(driverLicense.getId()).ifPresent(builder::id);
        builder
                .user(driverLicense.getUserDetailsId())
                .number(driverLicense.getNumber())
                .issueDate(driverLicense.getIssueDate())
                .expiredDate(driverLicense.getExpiredDate());
        return builder.build();
    }
}