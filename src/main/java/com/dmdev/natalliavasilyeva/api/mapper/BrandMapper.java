package com.dmdev.natalliavasilyeva.api.mapper;


import com.dmdev.natalliavasilyeva.api.dto.requestdto.BrandDto;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.CarDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.BrandResponseDTO;
import com.dmdev.natalliavasilyeva.domain.jpa.BrandJpa;
import com.dmdev.natalliavasilyeva.domain.model.Brand;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class BrandMapper {

    private BrandMapper() {
    }

    public static BrandResponseDTO toDto(Brand brand) {
        return new BrandResponseDTO(
                brand.getName(),
                ModelMapper.toDtos(brand.getModels())
        );
    }

    public static List<BrandResponseDTO> toDtos(List<Brand> brands) {
        return brands.isEmpty() ? Collections.emptyList() : brands.stream().map(BrandMapper::toDto).collect(Collectors.toList());
    }

    public static Brand fromDto(BrandDto brandDto) {
        return new Brand.Builder()
                .name(brandDto.getName())
                .build();
    }

    public static Brand fromCarDto(CarDto carDto) {
        return new Brand.Builder()
                .name(carDto.getBrandName())
                .build();
    }

    public static Brand fromJpa(BrandJpa jpaBrand) {
        return new Brand.Builder()
                .id(jpaBrand.getId())
                .name(jpaBrand.getName())
                .build();
    }

    public static List<Brand> fromJpaList(List<BrandJpa> jpaBrands) {
        return jpaBrands.isEmpty() ? Collections.emptyList() : jpaBrands.stream().map(BrandMapper::fromJpa).collect(Collectors.toList());
    }

    public static BrandJpa toJpa(Brand brand) {
        var builder = new BrandJpa.Builder();
        Optional.ofNullable(brand.getId()).ifPresent(builder::id);
        builder.name(brand.getName());
        return builder.build();
    }
}