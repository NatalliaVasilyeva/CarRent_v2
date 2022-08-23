package com.dmdev.natalliavasilyeva.api.mapper;


import com.dmdev.natalliavasilyeva.api.dto.requestdto.CarDto;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.ModelDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.ModelResponseDTO;
import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ModelMapper {

    private ModelMapper() {
    }

    public static ModelResponseDTO toDto(Model model) {
        return new ModelResponseDTO(
                model.getId(),
                model.getName(),
                model.getBrand() == null ? "" : model.getBrand().getName(),
                model.getTransmission().name(),
                model.getEngineType().name(),
                model.getCategory().getName(),
                model.getCategory().getPrice() == null ? BigDecimal.ZERO : model.getCategory().getPrice().getSum()
        );
    }

    public static List<ModelResponseDTO> toDtos(List<Model> models) {
        return models.stream().map(ModelMapper::toDto).collect(Collectors.toList());
    }

    public static Model fromDto(ModelDto modelDto) {
        return new Model.Builder()
                .name(modelDto.getName())
                .transmission(Transmission.getEnum(modelDto.getTransmission()))
                .engine(EngineType.getEnum(modelDto.getEngineType()))
                .brand(new Brand.Builder().name(modelDto.getBrand()).build())
                .category(modelDto.getCategory() == null ? null : new Category.Builder().name(modelDto.getCategory()).build())
                .build();
    }

    public static Model fromCarDto(CarDto carDto) {
        return new Model.Builder()
                .name(carDto.getModelName())
                .transmission(Transmission.getEnum(carDto.getTransmission()))
                .engine(EngineType.getEnum(carDto.getEngineType()))
                .brand(new Brand.Builder().name(carDto.getBrandName()).build())
                .build();
    }

    public static Model fromJpa(ModelJpa modelJpa) {
        return new Model.Builder()
                .id(modelJpa.getId())
                .name(modelJpa.getName())
                .brand(new Brand.Builder().id(modelJpa.getBrandId()).build())
                .transmission(modelJpa.getTransmission())
                .engine(modelJpa.getEngineType())
                .category(new Category.Builder().id(modelJpa.getId()).build())
                .build();
    }

    public static List<Model> fromJpaList(List<ModelJpa> jpaModels) {
        return jpaModels.isEmpty() ? Collections.emptyList() : jpaModels.stream().map(ModelMapper::fromJpa).collect(Collectors.toList());
    }

    public static ModelJpa toJpa(Model model) {
        var builder = new ModelJpa.Builder();
        Optional.ofNullable(model.getId()).ifPresent(builder::id);
        Optional.ofNullable(model.getCategory().getId()).ifPresent(builder::category);
        Optional.ofNullable(model.getBrand().getId()).ifPresent(builder::brand);
        builder.name(model.getName())
                .transmission(model.getTransmission())
                .engine(model.getEngineType());
        return builder.build();
    }
}