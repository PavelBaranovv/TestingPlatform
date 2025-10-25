package com.javarush.baranov.testingplatform.util;

import com.javarush.baranov.testingplatform.entity.AuthenticationAttempts;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.*;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties hibernateProperties = getProperties();

                Configuration configuration = new Configuration();
                configuration.setProperties(hibernateProperties);

                addAnnotatedClasses(configuration);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create Hibernate SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    private static Properties getProperties() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/testing_platform");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        dataSource.setMaximumPoolSize(20);
        dataSource.setConnectionTimeout(30000);

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.connection.datasource", dataSource);
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.put("hibernate.show_sql", "true");
        hibernateProperties.put("hibernate.format_sql", "true");
        hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
        hibernateProperties.put("hibernate.current_session_context_class", "thread");
        return hibernateProperties;
    }

    private static void addAnnotatedClasses(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(AuthenticationAttempts.class);
        configuration.addAnnotatedClass(Test.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(AnswerOption.class);
        configuration.addAnnotatedClass(StudentAttempt.class);
        configuration.addAnnotatedClass(StudentAnswer.class);
    }
}
