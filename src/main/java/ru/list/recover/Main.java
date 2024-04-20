package ru.list.recover;

import java.sql.SQLException;

import liquibase.exception.LiquibaseException;

public class Main {

    /**
     * @param args
     * @throws LiquibaseException 
     * @throws SQLException 
     */
    public static void main(String[] args) {

        Container container = new Container();
        container.run();
    }

}