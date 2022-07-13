package com.dmdev.natalliavasilyeva.connection;

import com.dmdev.natalliavasilyeva.connection.utils.DatabasePropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreator {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionCreator.class);

    private static ConnectionCreator instance;
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER = "db.user";
    private static final String DATABASE_PASSWORD = "db.password";
    private static final String DATABASE_DRIVER = "db.driver";

    private ConnectionCreator() {
        loadDriver();
    }

    static ConnectionCreator getInstance() {
        if (instance == null) {
            instance = new ConnectionCreator();
        }
        return instance;
    }

    private static void loadDriver() {
        try {
            Class.forName(DatabasePropertyUtils.get(DATABASE_DRIVER));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Database driver has not been loaded into app %s", e.getMessage()), e);
        }
    }

    public static Connection openConnection() throws SQLException {

        return DriverManager.getConnection(DatabasePropertyUtils.get(DATABASE_URL),
                DatabasePropertyUtils.get(DATABASE_USER),
                DatabasePropertyUtils.get(DATABASE_PASSWORD));
    }
}