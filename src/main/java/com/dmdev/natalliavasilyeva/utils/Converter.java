package com.dmdev.natalliavasilyeva.utils;

import com.dmdev.natalliavasilyeva.domain.jpa.OrderJpa;
import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.domain.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Converter {

    private Converter() {
    }

    public static Map<Long, UserJpa> convertUserJpaListToMap(List<UserJpa> list) {
        return list.stream()
                .collect(Collectors.toMap(UserJpa::getId, Function.identity()));
    }

    public static Map<Long, User> convertUserListToMapByUserDetailsId(List<User> list) {
        return list.stream()
                .collect(Collectors.toMap(User::getUserDetailsId, Function.identity()));
    }

    public static Map<Long, OrderJpa> convertOrderJpaListToMap(List<OrderJpa> list) {
        return list.stream()
                .collect(Collectors.toMap(OrderJpa::getId, Function.identity()));
    }

    public static Map<Long, Order> convertOrderListToMapByUserId(List<Order> list) {
        Map<Long, Order> map = new HashMap<>();
        for (Order order : list) {
            map.put(order.getUser().getId(), order);
        }
        return map;
    }

    public static Map<Long, Order> convertOrderListToMapByCarId(List<Order> list) {
        Map<Long, Order> map = new HashMap<>();
        for (Order order : list) {
            map.put(order.getCar().getId(), order);
        }
        return map;
    }

    public static String convertToLowerCase(String value) {
        return value.toLowerCase();
    }
}