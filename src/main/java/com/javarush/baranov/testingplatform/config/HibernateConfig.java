package com.javarush.baranov.testingplatform.config;

import com.javarush.baranov.testingplatform.entity.AuthenticationAttempts;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;
import static org.hibernate.cfg.AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS;
import static org.hibernate.cfg.FetchSettings.DEFAULT_BATCH_FETCH_SIZE;
import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.SchemaToolingSettings.HBM2DDL_AUTO;
import static org.hibernate.tool.schema.Action.*;

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
        Properties properties = new Properties();
        properties.put(CONNECTION_PROVIDER, "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");

        properties.put("hibernate.hikari.jdbcUrl", DatabaseCredentials.URL);
        properties.put("hibernate.hikari.username", DatabaseCredentials.USERNAME);
        properties.put("hibernate.hikari.password", DatabaseCredentials.PASSWORD);
        properties.put("hibernate.hikari.connectionTimeout", "20000");
        properties.put("hibernate.hikari.maximumPoolSize", "20");
        properties.put("hibernate.hikari.driverClassName", "org.postgresql.Driver");

        properties.put(DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(DEFAULT_BATCH_FETCH_SIZE, "16");

        properties.put(HBM2DDL_AUTO, VALIDATE);

        properties.put(SHOW_SQL, "TRUE");
        properties.put(FORMAT_SQL, "TRUE");
        properties.put(HIGHLIGHT_SQL, "TRUE");

        return properties;
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
