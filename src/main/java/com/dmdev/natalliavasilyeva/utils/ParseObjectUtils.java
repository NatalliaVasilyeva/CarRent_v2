package com.dmdev.natalliavasilyeva.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
                    field.setAccessible(true);
                    values.add(field.get(o));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(String.format("Wrong attempt to get object fields %s", e.getCause()), e);
                }
            }
        });
        return values;
    }

    public static List<String> getFieldObjectName(Class<?> o) {
//        Class<?> c = o.getClass();
        List<Field> fields = getAllFields(o);
        List<String> values = new LinkedList<>();
        fields.forEach(field -> {
            values.add(field.getName());
        });
        return values;
    }

    public static List<String> getFieldObjectValue(Object o) {
        Class<?> c = o.getClass();
        List<Field> fields = getAllFields(c);
        List<String> values = new LinkedList<>();
        fields.forEach(field -> {
            try {
                field.setAccessible(true);
                values.add(field.get(o) == null ? "" : field.get(o).toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(String.format("Wrong attempt to get object fields %s", e.getCause()), e);
            }
        });
        return values;
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new LinkedList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}