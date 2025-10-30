package com.javarush.baranov.testingplatform.config;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.javarush.baranov.testingplatform.config.DatabaseCredentials.*;

public class LiquibaseUpdate {

    @SneakyThrows
    public static void update() {
        try (ClassLoaderResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(
                LiquibaseUpdate.class.getClassLoader())) {

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD);
            JdbcConnection jdbcConnection = new JdbcConnection(connection);

            Liquibase liquibase = new Liquibase("changelogs-master.yaml",
                    resourceAccessor,
                    jdbcConnection
            );
            liquibase.update();
        }
    }
}
