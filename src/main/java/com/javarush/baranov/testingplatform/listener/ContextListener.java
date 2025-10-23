package com.javarush.baranov.testingplatform.listener;

import com.javarush.baranov.testingplatform.dao.AuthenticationAttemptsDao;
import com.javarush.baranov.testingplatform.dao.UserDao;
import com.javarush.baranov.testingplatform.service.AuthenticationAttemptsService;
import com.javarush.baranov.testingplatform.service.AuthenticationService;
import com.javarush.baranov.testingplatform.service.UserService;
import com.javarush.baranov.testingplatform.util.CredentialsExtractor;
import com.javarush.baranov.testingplatform.util.HibernateConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

        UserDao userDao = new UserDao(sessionFactory);
        UserService userService = new UserService(userDao);

        CredentialsExtractor credentialsExtractor = new CredentialsExtractor();
        AuthenticationAttemptsDao authAttemptsDao = new AuthenticationAttemptsDao(sessionFactory);
        AuthenticationAttemptsService authAttemptsService = new AuthenticationAttemptsService(authAttemptsDao);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AuthenticationService authService = new AuthenticationService(userService, authAttemptsService, credentialsExtractor, encoder);


        context.setAttribute("authenticationService", authService);
        context.setAttribute("credentialsExtractor", credentialsExtractor);
    }
}
