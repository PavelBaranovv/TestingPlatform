package com.javarush.baranov.testingplatform.service.student;

import com.javarush.baranov.testingplatform.constants.Route;
import com.javarush.baranov.testingplatform.dao.StudentAttemptDao;
import com.javarush.baranov.testingplatform.dao.TestDao;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.StudentAttempt;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.service.ResultsViewService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class SolvedTestsService {
    private final StudentAttemptDao attemptDao;
    private final TestDao testDao;
    private final ResultsViewService resultsViewService;

    public void setTestsAttribute(HttpServletRequest req) {
        User student = (User) req.getSession().getAttribute("user");
        List<StudentAttempt> attempts = attemptDao.getUserAttempts(student);
        req.setAttribute("attempts", attempts);
    }

    public void processChoice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choice = req.getParameter("choice");
        if ("view_attempt".equals(choice)) {

            String attemptIdStr = req.getParameter("attempt_id");
            Long attemptId = Long.parseLong(attemptIdStr);

            StudentAttempt attempt = attemptDao.getAttemptWithQuestionsAndAnswers(attemptId);

            String testId = attempt.getTest().getId();

            Test test = testDao.getTestWithQuestions(testId);

            resultsViewService.showTestResults(test, attempt, req, resp);
        } else if ("finish_view".equals(choice)){
            resp.sendRedirect(req.getRequestURI());
        } else {
            resp.sendRedirect(req.getContextPath() + Route.TEACHER_HOME);
        }
    }
}
