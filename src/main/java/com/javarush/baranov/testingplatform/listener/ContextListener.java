package com.javarush.baranov.testingplatform.listener;

import com.javarush.baranov.testingplatform.config.HibernateConfig;
import com.javarush.baranov.testingplatform.config.LiquibaseUpdate;
import com.javarush.baranov.testingplatform.dao.*;
import com.javarush.baranov.testingplatform.service.ResultsViewService;
import com.javarush.baranov.testingplatform.service.TestService;
import com.javarush.baranov.testingplatform.service.UserService;
import com.javarush.baranov.testingplatform.service.auth.AuthenticationAttemptsService;
import com.javarush.baranov.testingplatform.service.auth.AuthenticationService;
import com.javarush.baranov.testingplatform.service.student.SolvedTestsService;
import com.javarush.baranov.testingplatform.service.student.StudentAttemptService;
import com.javarush.baranov.testingplatform.service.student.TestSolvingService;
import com.javarush.baranov.testingplatform.service.teacher.AttemptsViewService;
import com.javarush.baranov.testingplatform.service.teacher.QuestionFillingService;
import com.javarush.baranov.testingplatform.service.teacher.TeacherHomeService;
import com.javarush.baranov.testingplatform.service.teacher.TestCreationService;
import com.javarush.baranov.testingplatform.util.*;
import com.javarush.baranov.testingplatform.util.validator.CredentialsValidator;
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

        LiquibaseUpdate.update();

        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

        UserDao userDao = new UserDao(sessionFactory);
        TestDao testDao = new TestDao(sessionFactory);
        QuestionDao questionDao = new QuestionDao(sessionFactory);
        StudentAttemptDao studentAttemptDao = new StudentAttemptDao(sessionFactory);
        AuthenticationAttemptsDao authAttemptsDao = new AuthenticationAttemptsDao(sessionFactory);

        UserService userService = new UserService(userDao);
        TestService testService = new TestService(testDao);

        AuthenticationAttemptsService authAttemptsService = new AuthenticationAttemptsService(authAttemptsDao);
        CredentialsExtractor credentialsExtractor = new CredentialsExtractor();
        CredentialsValidator credentialsValidator = new CredentialsValidator();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AuthenticationService authService = new AuthenticationService(userService, authAttemptsService, credentialsExtractor, credentialsValidator, encoder);

        BaseTestAttributesExtractor baseAttributesExtractor = new BaseTestAttributesExtractor();
        TestSettingsExtractor settingsExtractor = new TestSettingsExtractor();
        TestCreationService testCreationService = new TestCreationService(testService, baseAttributesExtractor, settingsExtractor);
        QuestionFillingService questionFillingService = new QuestionFillingService(questionDao, testDao);
        TeacherHomeService teacherHomeService = new TeacherHomeService(testService);

        StudentAttemptService studentAttemptService = new StudentAttemptService(studentAttemptDao);
        ResultsViewService resultsViewService = new ResultsViewService();
        TestIdExtractor testIdExtractor = new TestIdExtractor();
        AttemptAttributesExtractor attemptAttributesExtractor = new AttemptAttributesExtractor();
        TestSolvingService testSolvingService = new TestSolvingService(testDao, attemptAttributesExtractor, studentAttemptService, resultsViewService, testIdExtractor);
        SolvedTestsService solvedTestsService = new SolvedTestsService(studentAttemptDao, testDao, resultsViewService);

        AttemptsViewService attemptsViewService = new AttemptsViewService(testDao, studentAttemptDao, resultsViewService, testIdExtractor);

        context.setAttribute("authenticationService", authService);

        context.setAttribute("testService", testService);
        context.setAttribute("testIdExtractor", testIdExtractor);

        context.setAttribute("testCreationService", testCreationService);
        context.setAttribute("questionFillingService", questionFillingService);
        context.setAttribute("teacherHomeService", teacherHomeService);
        context.setAttribute("attemptsViewService", attemptsViewService);

        context.setAttribute("testSolvingService", testSolvingService);
        context.setAttribute("solvedTestsService", solvedTestsService);
    }
}