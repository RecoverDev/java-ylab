package ru.list.recover.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ru.list.recover.logger.Logger;

public class DBConnection {
    private Logger logger;
    private FitnessProperties properties;
    private Connection connection;

    public DBConnection(FitnessProperties properties, Logger logger) {
        this.properties = properties;
        this.logger = logger;
    }

    public boolean connect() {
        boolean result = false;

        try {
            connection = DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());
            result = true;
            logger.addRecord("Успешно подключилмсь к базе данных", result);
        } catch(SQLException e) {
            logger.addRecord("Ошибка подключения к базе данных " + e.getMessage(), result);
        }

        return result;

    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch(SQLException e) {

        }
    }





}
