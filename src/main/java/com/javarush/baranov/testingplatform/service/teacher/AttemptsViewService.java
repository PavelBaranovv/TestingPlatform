package com.javarush.baranov.testingplatform.service.teacher;

import com.javarush.baranov.testingplatform.constants.Route;
import com.javarush.baranov.testingplatform.dao.StudentAttemptDao;
import com.javarush.baranov.testingplatform.dao.TestDao;
import com.javarush.baranov.testingplatform.entity.tests.StudentAttempt;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.service.ResultsViewService;
import com.javarush.baranov.testingplatform.util.TestIdExtractor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class AttemptsViewService {

    private final TestDao testDao;
    private final StudentAttemptDao attemptDao;
    private final ResultsViewService resultsViewService;
    private final TestIdExtractor idExtractor;

    public void setTestAttribute(HttpServletRequest req) {
        String testId = idExtractor.extract(req.getRequestURI());
        Test test = testDao.getTestWithAttemptsAndUsers(testId);
        req.setAttribute("test", test);
    }

    public void processChoice(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String choice = req.getParameter("choice");
        if ("view_attempt".equals(choice)) {
            String testId = idExtractor.extract(req.getRequestURI());
            String attemptIdStr = req.getParameter("attempt_id");
            Long attemptId = Long.parseLong(attemptIdStr);

            Test test = testDao.getTestWithQuestions(testId);
            StudentAttempt attempt = attemptDao.getAttemptWithQuestionsAndAnswers(attemptId);

            resultsViewService.showTestResults(test, attempt, req, resp);
        } else if ("finish_view".equals(choice)){
            resp.sendRedirect(req.getRequestURI());
        } else {
            resp.sendRedirect(req.getContextPath() + Route.TEACHER_HOME);
        }
    }
}
