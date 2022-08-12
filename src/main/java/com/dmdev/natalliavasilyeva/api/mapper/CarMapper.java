package com.dmdev.natalliavasilyeva.api.mapper;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.CarDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarAdminResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.ModelResponseDTO;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Color;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;
import com.dmdev.natalliavasilyeva.service.FilesService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CarMapper {

    private CarMapper() {
    }

    private static final FilesService filesService = ServiceFactory.getInstance().getFilesService();

    public static CarUserResponseDto toShotDto(Car car) {
        ModelResponseDTO modelResponseDTO = ModelMapper.toDto(car.getModel());
        CarUserResponseDto carUserResponseDto = new CarUserResponseDto(
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

        carUserResponseDto.setImageContent(car.getImage() == null? null : filesService.upload(car.getImage()));
        return carUserResponseDto;
    }

    public static CarUserResponseDto toShotDtoForUserList(Car car) {
        ModelResponseDTO modelResponseDTO = ModelMapper.toDto(car.getModel());
        var carUserResponseDto = new CarUserResponseDto(
                car.getId(),
                modelResponseDTO.getBrandName(),
                car.getModel().getName(),
                car.getImage(),
                modelResponseDTO.getPrice()
        );

        carUserResponseDto.setImageContent(car.getImage() == null? null : filesService.upload(car.getImage()));
        return carUserResponseDto;
    }

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

    public static List<CarUserResponseDto> toShotDtosForUserList(List<Car> cars) {
        return cars.stream().map(CarMapper::toShotDtoForUserList).collect(Collectors.toList());
    }

    public static List<CarAdminResponseDto> toDtos(List<Car> cars) {
        return cars.stream().map(CarMapper::toDto).collect(Collectors.toList());
    }


    public static Car fromDto(CarDto carCreateDto) {
        return new Car.Builder()
                .model(new Model.Builder()
                        .name(carCreateDto.getModelName())
                        .transmission(Transmission.getEnum(carCreateDto.getTransmission()))
                        .engine(EngineType.getEnum(carCreateDto.getEngineType()))
                        .brand(new Brand.Builder().name(carCreateDto.getBrandName()).build())
                        .category(new Category.Builder().build())
                        .build())
                .color(Color.getEnum(carCreateDto.getColor()))
                .year(carCreateDto.getYearOfProduction())
                .number(carCreateDto.getNumber())
                .vin(carCreateDto.getVin())
                .repaired(carCreateDto.isRepaired())
                .image(carCreateDto.getBrandName() + "/" + carCreateDto.getImageName())
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
        return jpaCars.isEmpty() ? Collections.emptyList() : jpaCars.stream().map(CarMapper::fromJpa).collect(Collectors.toList());
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