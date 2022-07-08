package com.dmdev.natalliavasilyeva.persistence.repository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class ParseObjectUtils {

    private ParseObjectUtils() {
    }

    public static List<Object> getFieldObjectsWithoutId(Object o) {
        Class<?> c = o.getClass();
        Field[] fields = c.getDeclaredFields();
        List<Object> values = new LinkedList<>();
        Arrays.stream(fields).forEach(field -> {
            if (!field.getName().equals("id")) {
                try {
                    values.add(field.get(o));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return values;
    }
}