package com.dmdev.natalliavasilyeva.connection.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public final class DatabasePropertyUtils {
    private static final Logger logger = LoggerFactory.getLogger(DatabasePropertyUtils.class);
    private static final String DATABASE_PROPERTY = "database.properties";
    protected static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private DatabasePropertyUtils() {
    }

    public static String get(String propertyName) {
        return PROPERTIES.getProperty(propertyName);
    }

    private static void loadProperties() {
        try (var inputStream = DatabasePropertyUtils.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTY)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Something wrong with load connection pool properties %s", e.getCause()), e);
        }
    }
}