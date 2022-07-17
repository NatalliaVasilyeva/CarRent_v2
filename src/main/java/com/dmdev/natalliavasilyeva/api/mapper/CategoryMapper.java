package com.dmdev.natalliavasilyeva.api.mapper;


import com.dmdev.natalliavasilyeva.api.dto.requestdto.CategoryDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CategoryResponseDto;
import com.dmdev.natalliavasilyeva.domain.jpa.BrandJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.CategoryJpa;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Price;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CategoryMapper {

    public static CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(
                category.getName(),
                category.getPrice().getSum()
        );
    }

    public static List<CategoryResponseDto> toDtos(List<Category> categories) {
        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }

    public static Category fromDto(CategoryDto categoryDto) {
        return new Category.Builder()
                .name(categoryDto.getName())
                .price(new Price.Builder().id(categoryDto.getPriceId()).sum(categoryDto.getPriceSum()).build())
                .build();
    }

    public static Category fromJpa(CategoryJpa jpaCategory) {
        return new Category.Builder()
                .id(jpaCategory.getId())
                .name(jpaCategory.getName())
                .price(new Price.Builder().id(jpaCategory.getId()).build())
                .build();
    }

    public static List<Category> fromJpaList(List<CategoryJpa> jpaCategories) {
        return jpaCategories.size() == 0 ? Collections.emptyList() : jpaCategories.stream().map(CategoryMapper::fromJpa).collect(Collectors.toList());
    }

    public static CategoryJpa toJpa(Category category) {
        var builder = new CategoryJpa.Builder();
        Optional.ofNullable(category.getId()).ifPresent(builder::id);
        builder
                .name(category.getName())
                .price(category.getId())
                .build();
        return builder.build();
    }
}