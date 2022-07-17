package com.dmdev.natalliavasilyeva.api.mapper;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.CarDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarAdminResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.ModelResponseDTO;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Color;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarMapper {

    private static final String IMAGE_FOLDER = "/cars";

//    public static CarUserResponseDto toShotDto(Car car) {
//        return new CarUserResponseDto(
//
//                car.getModel().getBrand().getName(),
//                ModelMapper.toDto(car.getModel()),
//                car.getColor().name(),
//                car.getYearOfProduction(),
//                car.getImage(),
//                car.getModel().getCategory().getPrice().getSum()
//        );
//    }

    public static CarUserResponseDto toShotDto(Car car) {
        ModelResponseDTO modelResponseDTO = ModelMapper.toDto(car.getModel());
        return new CarUserResponseDto(
                car.getId(),
                modelResponseDTO.getBrandName(),
                car.getModel().getName(),
                modelResponseDTO.getTransmission(),
                modelResponseDTO.getEngineType(),
                car.getColor().name(),
                car.getYearOfProduction(),
                car.getImage(),
                car.getModel().getCategory().getPrice().getSum()
        );
    }

//    public static CarAdminResponseDto toDto(Car car) {
//        return new CarAdminResponseDto(
//                car.getModel().getBrand().getName(),
//                ModelMapper.toDto(car.getModel()),
//                car.getColor().name(),
//                car.getYearOfProduction(),
//                car.getImage(),
//                car.getModel().getCategory().getPrice().getSum(),
//                car.getNumber(),
//                car.getVin(),
//                car.isRepaired(),
//                AccidentMapper.toDtos(car.getAccidents()),
//                null
//        );
//    }

    public static CarAdminResponseDto toDto(Car car) {
        ModelResponseDTO modelResponseDTO = ModelMapper.toDto(car.getModel());
        return new CarAdminResponseDto(
                car.getId(),
                modelResponseDTO.getBrandName(),
                car.getModel().getName(),
                modelResponseDTO.getTransmission(),
                modelResponseDTO.getEngineType(),
                car.getColor().name(),
                car.getYearOfProduction(),
                car.getImage(),
                car.getModel().getCategory().getPrice().getSum(),
                car.getNumber(),
                car.getVin(),
                car.isRepaired(),
                AccidentMapper.toDtos(car.getAccidents()),
                null
        );
    }

    public static List<CarUserResponseDto> toShotDtos(List<Car> cars) {
        return cars.stream().map(CarMapper::toShotDto).collect(Collectors.toList());
    }

    public static List<CarAdminResponseDto> toDtos(List<Car> cars) {
        return cars.stream().map(CarMapper::toDto).collect(Collectors.toList());
    }


    public static Car fromDto(CarDto carCreateDto) {
        return new Car.Builder()
                .model(new Model.Builder()
                        .name(carCreateDto.getModelName())
                        .transmission(Transmission.valueOf(carCreateDto.getTransmission()))
                        .engine(EngineType.valueOf(carCreateDto.getEngineType()))
                        .brand(new Brand.Builder().name(carCreateDto.getBrandName()).build())
                        .category(new Category.Builder().build())
                        .build())
                .color(Color.valueOf(carCreateDto.getColor()))
                .year(carCreateDto.getYearOfProduction())
                .number(carCreateDto.getNumber())
                .vin(carCreateDto.getVin())
                .repaired(carCreateDto.isRepaired())
                .image(IMAGE_FOLDER + carCreateDto.getImage().getSubmittedFileName())
                .content(carCreateDto.getImage())
                .build();
    }


    public static Car fromJpa(CarJpa carJpa) {
        return new Car.Builder()
                .id(carJpa.getId())
                .model(new Model.Builder().id(carJpa.getId()).build())
                .color(carJpa.getColor())
                .year(carJpa.getYearOfProduction())
                .number(carJpa.getNumber())
                .vin(carJpa.getVin())
                .repaired(carJpa.isRepaired())
                .image(carJpa.getImage())
                .build();
    }

    public static List<Car> fromJpaList(List<CarJpa> jpaCars) {
        return jpaCars.size() == 0 ? Collections.emptyList() : jpaCars.stream().map(CarMapper::fromJpa).collect(Collectors.toList());
    }

    public static CarJpa toJpa(Car car) {
        var builder = new CarJpa.Builder();
        Optional.ofNullable(car.getId()).ifPresent(builder::id);
        builder
                .model(car.getModel().getId())
                .color(car.getColor())
                .year(car.getYearOfProduction())
                .number(car.getNumber())
                .vin(car.getVin())
                .repaired(car.isRepaired())
                .image(car.getImage());
        return builder.build();
    }
}