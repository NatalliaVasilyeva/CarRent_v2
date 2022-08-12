package com.dmdev.natalliavasilyeva.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FilesService {
    private static final String IMAGE_PATH_DIRECTORY = "/Users/natallia.vasilyeva/myProjects/CarRent/src/main/resources/images";

    public void downloadImage(String imagePath, InputStream imageContent) {
        var fullImagePath = Path.of(IMAGE_PATH_DIRECTORY, imagePath);
        try {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, imageContent.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String upload(String imagePath) {
        var fullImagePath = Path.of(IMAGE_PATH_DIRECTORY, imagePath);
        try {
            Optional<InputStream> inputStream = Files.exists(fullImagePath)
                    ? Optional.of(Files.newInputStream(fullImagePath))
                    : Optional.empty();

            if (inputStream.isPresent()) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inputStream.get().read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] imageBytes = outputStream.toByteArray();
                return Base64.getEncoder().encodeToString(imageBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}