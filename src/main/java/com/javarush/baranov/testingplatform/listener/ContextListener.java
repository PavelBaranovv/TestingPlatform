package com.javarush.baranov.testingplatform.listener;

import com.javarush.baranov.testingplatform.dao.*;
import com.javarush.baranov.testingplatform.service.ResultsViewService;
import com.javarush.baranov.testingplatform.service.StudentAttemptsService;
import com.javarush.baranov.testingplatform.service.auth.AuthenticationAttemptsService;
import com.javarush.baranov.testingplatform.service.auth.AuthenticationService;
import com.javarush.baranov.testingplatform.service.UserService;
import com.javarush.baranov.testingplatform.service.student.TestSolvingService;
import com.javarush.baranov.testingplatform.service.teacher.QuestionFillingService;
import com.javarush.baranov.testingplatform.service.teacher.TestCreationService;
import com.javarush.baranov.testingplatform.service.TestService;
import com.javarush.baranov.testingplatform.util.*;
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

        TestDao testDao = new TestDao(sessionFactory);
        BaseTestAttributesExtractor baseAttributesExtractor = new BaseTestAttributesExtractor();
        TestSettingsExtractor settingsExtractor = new TestSettingsExtractor();
        TestService testService = new TestService(testDao);
        TestCreationService testCreationService = new TestCreationService(testService, baseAttributesExtractor, settingsExtractor);

        QuestionDao questionDao = new QuestionDao(sessionFactory);
        QuestionFillingService questionFillingService = new QuestionFillingService(questionDao, testDao);

        AttemptAttributesExtractor attemptAttributesExtractor = new AttemptAttributesExtractor();
        StudentAttemptDao studentAttemptDao = new StudentAttemptDao(sessionFactory);
        StudentAttemptsService studentAttemptsService = new StudentAttemptsService(studentAttemptDao);
        ResultsViewService resultsViewService = new ResultsViewService();
        TestSolvingService testSolvingService = new TestSolvingService(testDao, attemptAttributesExtractor, studentAttemptsService, resultsViewService);

        context.setAttribute("testService", testService);

        context.setAttribute("authenticationService", authService);
        context.setAttribute("credentialsExtractor", credentialsExtractor);

        context.setAttribute("testCreationService", testCreationService);
        context.setAttribute("questionFillingService", questionFillingService);

        context.setAttribute("testSolvingService", testSolvingService);
    }
}
