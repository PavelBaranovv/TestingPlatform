package com.javarush.baranov.testingplatform.service;

import com.javarush.baranov.testingplatform.constants.WebResource;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.StudentAttempt;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.enums.Role;
import com.javarush.baranov.testingplatform.enums.TestShowResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.javarush.baranov.testingplatform.constants.WebResource.RESULTS_THANKS_PAGE_JSP;

public class ResultsViewService {

    public void showTestResults(Test test, StudentAttempt attempt, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("test", test);
        req.setAttribute("attempt", attempt);

        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() == Role.TEACHER) {
            showFullResult(req, resp);
        } else {
            TestShowResult resultsView = test.getShowResult();
            switch (resultsView) {
                case NOTHING -> showThanksPage(req, resp);
                case ONLY_SCORE -> showScore(req, resp);
                case ONLY_MISTAKES -> showOnlyMistakesResult(req, resp);
                case MISTAKES_AND_CORRECTS -> showFullResult(req, resp);
            }
        }
    }

    private void showThanksPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(RESULTS_THANKS_PAGE_JSP).forward(req, resp);
    }

    private void showScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WebResource.RESULTS_ONLY_SCORE_JSP).forward(req, resp);
    }

    private void showOnlyMistakesResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WebResource.RESULTS_ONLY_MISTAKES_JSP).forward(req, resp);
    }

    private void showFullResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WebResource.RESULTS_FULL_JSP).forward(req, resp);
    }
}
