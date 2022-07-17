package com.dmdev.natalliavasilyeva.utils;

import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.domain.model.User;

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
}