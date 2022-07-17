package com.dmdev.natalliavasilyeva.api.mapper;


import com.dmdev.natalliavasilyeva.api.dto.requestdto.AccidentDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.AccidentResponseDto;
import com.dmdev.natalliavasilyeva.domain.model.Accident;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;


public final class AccidentMapper {

    public static AccidentResponseDto toDto(Accident accident) {
        return new AccidentResponseDto(
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
}