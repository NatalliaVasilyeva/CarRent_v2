package com.dmdev.natalliavasilyeva.api.mapper;


import com.dmdev.natalliavasilyeva.api.dto.requestdto.AccidentDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.AccidentResponseDto;
import com.dmdev.natalliavasilyeva.domain.jpa.AccidentJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.BrandJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserDetailsJpa;
import com.dmdev.natalliavasilyeva.domain.model.Accident;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class AccidentMapper {
    private AccidentMapper() {
    }
    public static AccidentResponseDto toDto(Accident accident) {
        return new AccidentResponseDto(
                accident.getId(),
                LocalDateTime.ofInstant(accident.getDate(), ZoneOffset.UTC),
                String.format("brand %s model %s",
                        accident.getBrand(),
                        accident.getModel()),
                accident.getCarNumber(),
                String.format("%s %s", accident.getUserFirstName(), accident.getUserLastName()),
                accident.getDescription(),
                accident.getDamage()
        );
    }

    public static List<AccidentResponseDto> toDtos(List<Accident> accidents) {
        return accidents.stream().map(AccidentMapper::toDto).collect(Collectors.toList());
    }

    public static Accident fromDto(AccidentDto accidentDto) {
        return new Accident.Builder()
                .order(accidentDto.getOrderId())
                .date(accidentDto.getAccidentDate().toInstant(ZoneOffset.UTC))
                .description(accidentDto.getDescription())
                .damage(accidentDto.getDamage())
                .build();
    }

    public static Accident fromJpa(AccidentJpa accidentJpa, BrandJpa brandJpa, ModelJpa modelJpa, CarJpa carJpa, UserDetailsJpa userDetailsJpa) {
        return new Accident.Builder()
                .id(accidentJpa.getId())
                .order(accidentJpa.getOrderId())
                .date(accidentJpa.getDate())
                .brand(brandJpa.getName())
                .model(modelJpa.getName())
                .car(carJpa.getNumber())
                .userName(userDetailsJpa.getName())
                .userSurname(userDetailsJpa.getSurname())
                .description(accidentJpa.getDescription())
                .damage(accidentJpa.getDamage())
                .build();
    }

    public static Accident fromJpa(AccidentJpa accidentJpa) {
        return new Accident.Builder()
                .id(accidentJpa.getId())
                .order(accidentJpa.getOrderId())
                .date(accidentJpa.getDate())
                .description(accidentJpa.getDescription())
                .damage(accidentJpa.getDamage())
                .build();
    }

    public static List<Accident> fromJpaList(List<AccidentJpa> jpaAccidents) {
        return jpaAccidents.isEmpty() ? Collections.emptyList() : jpaAccidents.stream().map(AccidentMapper::fromJpa).collect(Collectors.toList());
    }

    public static AccidentJpa toAccidentJpa(Accident accident) {
        var builder = new AccidentJpa.Builder();
        Optional.ofNullable(accident.getId()).ifPresent(builder::id);
        builder
                .order(accident.getOrderNumber())
                .date(accident.getDate())
                .description(accident.getDescription())
                .damage(accident.getDamage());
        return builder.build();
    }
}