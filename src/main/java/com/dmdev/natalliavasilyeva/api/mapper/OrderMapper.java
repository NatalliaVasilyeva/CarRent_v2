package com.dmdev.natalliavasilyeva.api.mapper;


import com.dmdev.natalliavasilyeva.api.dto.requestdto.OrderDto;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.OrderUserUpdateDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderShotResponseDto;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.CarRentalTimeJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.OrderJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.domain.model.CarRentalTime;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;
import com.dmdev.natalliavasilyeva.domain.model.Role;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.utils.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public final class OrderMapper {

    private OrderMapper() {
    }

    public static OrderShotResponseDto toShotDto(Order order) {
        return new OrderShotResponseDto(
                order.getId(),
                LocalDateTime.ofInstant(order.getDate(), ZoneOffset.UTC),
                order.getOrderStatus().name()
        );
    }

    public static OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                LocalDateTime.ofInstant(order.getDate(), ZoneOffset.UTC),
                order.getOrderStatus().name(),
                String.format("%s %s %s number %s",
                        order.getCar().getModel().getBrand().getName(),
                        order.getCar().getModel().getName(),
                        "\n",
                        order.getCar().getNumber()),
                order.getCarRentalTime().getStartRentalDate() == null ? null : LocalDateTime.ofInstant(order.getCarRentalTime().getStartRentalDate(), ZoneOffset.UTC),
                order.getCarRentalTime().getEndRentalDate() == null ? null : LocalDateTime.ofInstant(order.getCarRentalTime().getEndRentalDate(), ZoneOffset.UTC),
                order.isInsuranceNeeded(),
                order.getSum(),
                order.isWithAccident()
        );
    }

    public static List<OrderResponseDto> toDtos(List<Order> orders) {
        return orders.isEmpty()? Collections.emptyList() : orders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
    }

    public static List<OrderShotResponseDto> toShotDtos(List<Order> orders) {
        return orders.stream().map(OrderMapper::toShotDto).collect(Collectors.toList());
    }

    public static Order fromDto(OrderDto orderDto) {
        return new Order.Builder()
                .date(Instant.now())
                .carRentalTime(new CarRentalTime.Builder()
                        .start(orderDto.getStartRentalDate().toInstant(ZoneOffset.UTC))
                        .end(orderDto.getEndRentalDate().toInstant(ZoneOffset.UTC))
                        .build())
                .car(new Car.Builder().id(orderDto.getCarId()).build())
                .user(new User.Builder().userId(orderDto.getUserId()).build())
                .passport(orderDto.getPassport())
                .insurance(orderDto.isInsuranceNeeded())
                .orderStatus(OrderStatus.CONFIRMATION_WAIT)
                .build();
    }

    public static Order fromDto(OrderUserUpdateDto orderDto) {
        return new Order.Builder()
                .carRentalTime(new CarRentalTime.Builder()
                        .start(orderDto.getStartRentalDate().toInstant(ZoneOffset.UTC))
                        .end(orderDto.getEndRentalDate().toInstant(ZoneOffset.UTC))
                        .build())
                .insurance(orderDto.isInsuranceNeeded())
                .build();
    }

    public static Order fromJpa(OrderJpa orderJpa, CarRentalTimeJpa carRentalTimeJpa, UserJpa userJpa, CarJpa carJpa) {
        return new Order.Builder()
                .id(orderJpa.getId())
                .date(orderJpa.getDate())
                .carRentalTime(new CarRentalTime.Builder()
                        .start(carRentalTimeJpa.getStartRentalDate())
                        .end(carRentalTimeJpa.getEndRentalDate())
                        .build())
                .user(new User.Builder()
                        .userId(orderJpa.getUserId())
                        .role(Role.getEnum(userJpa.getRole())).build())
                .car(new Car.Builder()
                        .id(orderJpa.getCarId())
                        .number(carJpa.getNumber())
                        .image(carJpa.getImage()).build())
                .passport(orderJpa.getPassport())
                .insurance(orderJpa.isInsuranceNeeded())
                .orderStatus(orderJpa.getOrderStatus())
                .sum(orderJpa.getSum())
                .build();
    }

    public static Order fromJpa(OrderJpa orderJpa) {
        return new Order.Builder()
                .id(orderJpa.getId())
                .date(orderJpa.getDate())
                .user(new User.Builder().userId(orderJpa.getUserId()).build())
                .car(new Car.Builder().id(orderJpa.getCarId()).build())
                .passport(orderJpa.getPassport())
                .insurance(orderJpa.isInsuranceNeeded())
                .orderStatus(orderJpa.getOrderStatus())
                .sum(orderJpa.getSum())
                .build();
    }

    public static List<Order> fromJpaList(List<OrderJpa> jpaOrders) {
        return jpaOrders.isEmpty() ? Collections.emptyList() : jpaOrders.stream().map(OrderMapper::fromJpa).collect(Collectors.toList());
    }

    public static List<Order> fromJpaList(List<OrderJpa> orderJpas, List<CarRentalTimeJpa> carRentalTimeJpas, List<UserJpa> userJpas, List<CarJpa> carJpas) {
        if (orderJpas.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Order> first = mergeOrderAndCarRentalTimeJpaLists(orderJpas, carRentalTimeJpas);
            List<Order> second = mergeOrderWithUserLists(first, userJpas);
            return mergeOrderWithCarLists(second, carJpas);
        }
    }

    public static OrderJpa toOrderJpa(Order order) {
        var builder = new OrderJpa.Builder();
        Optional.ofNullable(order.getId()).ifPresent(builder::id);
        Optional.ofNullable(order.getSum()).ifPresent(builder::sum);
        builder
                .date(order.getDate())
                .user(order.getUser().getId())
                .car(order.getCar().getId())
                .passport(order.getPassport())
                .insurance(order.isInsuranceNeeded())
                .orderStatus(order.getOrderStatus());
        return builder.build();
    }

    public static CarRentalTimeJpa toCarRentalTimeJpa(Order order) {
        var builder = new CarRentalTimeJpa.Builder();
        Optional.ofNullable(order.getCarRentalTime().getId()).ifPresent(builder::id);

        Optional<Order> existOrder = Optional.ofNullable(order.getCarRentalTime().getOrder());
        existOrder.ifPresent(o -> builder.order(o.getId()));

        builder
                .start(order.getCarRentalTime().getStartRentalDate())
                .end(order.getCarRentalTime().getEndRentalDate());
        return builder.build();
    }

    private static List<Order> mergeOrderAndCarRentalTimeJpaLists(List<OrderJpa> orderJpas, List<CarRentalTimeJpa> carRentalTimeJpas) {
        return new ArrayList<>(Converter.convertOrderJpaListToMap(orderJpas)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        orderJpaEntry -> {
                            Order.Builder builder = new Order
                                    .Builder();
                            builder.id(orderJpaEntry.getValue().getId());
                            builder.date(orderJpaEntry.getValue().getDate());
                            builder.passport(orderJpaEntry.getValue().getPassport());
                            builder.insurance(orderJpaEntry.getValue().isInsuranceNeeded());
                            builder.sum(orderJpaEntry.getValue().getSum());
                            carRentalTimeJpas.stream().filter(crt -> crt.getOrderId() == orderJpaEntry.getKey())
                                    .forEach(cr -> builder.carRentalTime(new CarRentalTime.Builder()
                                            .id(cr.getId())
                                            .start(cr.getStartRentalDate())
                                            .end(cr.getEndRentalDate())
                                            .build()
                                    ));
                            return builder
                                    .build();
                        }
                ))
                .values());
    }

    private static List<Order> mergeOrderWithUserLists(List<Order> orders, List<UserJpa> userJpas) {
        List<Order> newOrderList = orders;
        return new ArrayList<>(Converter.convertOrderListToMapByUserId(newOrderList)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        orderEntry -> {
                            Optional<UserJpa> optional = userJpas.stream().filter(u -> u.getId() == orderEntry.getKey())
                                    .findFirst();

                            Order order = orderEntry.getValue();
                            if (optional.isEmpty()) {
                                order.setUser(null);
                            } else {
                                UserJpa us = optional.get();
                                order.setUser(
                                        new User.Builder()
                                                .login(us.getLogin())
                                                .role(Role.getEnum(us.getRole()))
                                                .build());
                            }
                            return order;
                        }
                ))
                .values());
    }

    private static List<Order> mergeOrderWithCarLists(List<Order> orders, List<CarJpa> carJpas) {
        List<Order> newOrderList = orders;
        return new ArrayList<>(Converter.convertOrderListToMapByCarId(newOrderList)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        orderEntry -> {
                            Optional<CarJpa> optional = carJpas.stream().filter(c -> c.getId() == orderEntry.getKey())
                                    .findFirst();

                            Order order = orderEntry.getValue();
                            if (optional.isEmpty()) {
                                order.setCar(null);
                            } else {
                                CarJpa car = optional.get();
                                order.setCar(
                                        new Car.Builder()
                                                .model(new Model.Builder().id(car.getModelId()).build())
                                                .color(car.getColor())
                                                .year(car.getYearOfProduction())
                                                .number(car.getNumber())
                                                .image(car.getImage())
                                                .build());
                            }
                            return order;
                        }
                ))
                .values());
    }
}