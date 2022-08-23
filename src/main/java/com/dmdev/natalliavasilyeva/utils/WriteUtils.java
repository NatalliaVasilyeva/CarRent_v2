package com.dmdev.natalliavasilyeva.utils;

import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderResponseDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class WriteUtils {
    public static void writeResult(Path path, List<OrderResponseDto> records) throws IOException {
        Files.createDirectories(path.getParent());
        Files.write(path, toHeaderRepresentation(OrderResponseDto.class).getBytes(), CREATE, TRUNCATE_EXISTING);
        Files.write(path, "\n".getBytes(), APPEND);
        Files.write(path, toContentRepresentation(records), APPEND);
    }


    private static List<String> toContentRepresentation(List<OrderResponseDto> orderResponseDtos) {
        return orderResponseDtos.stream()
                .map(ParseObjectUtils::getFieldObjectValue)
                .map(line -> String.join(",", line))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static String toHeaderRepresentation(Class<?> o) {
        return String.join(",", ParseObjectUtils.getFieldObjectName(o));
    }
}