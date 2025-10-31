package com.javarush.baranov.testingplatform.config;

public class DatabaseCredentials {

    public static final String URL = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:postgresql://localhost:5432/testing_platform"
    );

    public static final String USERNAME = System.getenv().getOrDefault(
            "DB_USER",
            "postgres"
    );

    public static final String PASSWORD = System.getenv().getOrDefault(
            "DB_PASSWORD",
            "root"
    );
}
