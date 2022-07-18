package com.dmdev.natalliavasilyeva.service;

import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FilesService {
    private final static Path IMAGE_PATH_DIRECTORY = Path.of("src", "main", "resources", "images");

    public void downloadImage(String imagePath, Part imageContent) {
        var fullImagePath = Path.of(IMAGE_PATH_DIRECTORY.toString(), imagePath);
        try (InputStream inputStream = imageContent.getInputStream()) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, inputStream.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<InputStream> upload(String imagePath) {
        var fullImagePath = Path.of(IMAGE_PATH_DIRECTORY.toString(), imagePath);
        try {
            return Files.exists(fullImagePath)
                    ? Optional.of(Files.newInputStream(fullImagePath))
                    : Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}