package ru.list.recover.db;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import ru.list.recover.logger.Logger;


public class FitnessProperties {
    private String url;
    private String user;
    private String password;

    private String fileName;

    private Logger logger;

    public FitnessProperties(String fileName, Logger logger) {
        this.fileName = fileName;
        this.logger = logger;
    }

    public boolean load() {
        Properties properties = new Properties();
        boolean result = false;

        File file = new File(fileName);
        try {
            properties.load(new FileReader(file));
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            result = true;
            logger.addRecord("Чтение файла свойств", result);

        } catch (IOException e) {
            logger.addRecord("Чтение файла свойств", result);
        }

        return result;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    


}
