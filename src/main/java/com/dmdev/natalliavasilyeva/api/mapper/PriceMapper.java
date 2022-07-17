package com.dmdev.natalliavasilyeva.api.mapper;


import com.dmdev.natalliavasilyeva.api.dto.requestdto.PriceDto;
import com.dmdev.natalliavasilyeva.domain.jpa.PriceJpa;
import com.dmdev.natalliavasilyeva.domain.model.Price;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class PriceMapper {

    public static Price fromDto(PriceDto priceDto) {
        return new Price.Builder()
                .sum(priceDto.getSum())
                .build();
    }

    public static Price fromJpa(PriceJpa priceJpa) {
        return new Price.Builder()
                .id(priceJpa.getId())
                .sum(priceJpa.getSum())
                .build();
    }

    public static List<Price> fromJpaList(List<PriceJpa> jpaPrices) {
        return jpaPrices.size() == 0 ? Collections.emptyList() : jpaPrices.stream().map(PriceMapper::fromJpa).collect(Collectors.toList());
    }

    public static PriceJpa toJpa(Price price) {
        var builder = new PriceJpa.Builder();
        builder.sum(price.getSum());
        Optional.ofNullable(price.getId()).ifPresent(builder::id);
        return builder.build();
    }
}