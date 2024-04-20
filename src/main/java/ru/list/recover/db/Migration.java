package ru.list.recover.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.list.recover.logger.Logger;

public class Migration {
    private Connection connection;
    private Logger logger;
    private String nameSchema;


    public Migration(Connection connection, Logger logger, String nameSchema) {
        this.connection = connection;
        this.logger = logger;
        this.nameSchema = nameSchema;
    }


    public void migrate() throws LiquibaseException {
        if (this.createSchema()) {
            try {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
                liquibase.update();
                liquibase.close();
            } catch(LiquibaseException e) {
                logger.addRecord("Ошибка при миграции: " + e.getMessage(), false);
            } 
        }
    }

    private boolean createSchema() {
        String sql = "CREATE SCHEMA IF NOT EXISTS " + nameSchema + " AUTHORIZATION CURRENT_USER; GRANT ALL ON SCHEMA " + nameSchema + " TO CURRENT_USER";
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            result = true;
            logger.addRecord("Создана схема " + nameSchema, result);
        } catch(SQLException e) {
            logger.addRecord("Ошибка при создании схемы: " + e.getMessage(), false);
        }

        return result;
    }

}
