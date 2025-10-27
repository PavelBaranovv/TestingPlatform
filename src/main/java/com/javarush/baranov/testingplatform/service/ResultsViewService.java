package com.javarush.baranov.testingplatform.service;

import com.javarush.baranov.testingplatform.entity.tests.StudentAttempt;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.enums.TestShowResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResultsViewService {
    public void showTestResults(Test test, StudentAttempt attempt, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("test", test);
        req.setAttribute("attempt", attempt);

        TestShowResult resultsView = test.getShowResult();
        switch (resultsView) {
            case NOTHING -> showThanksPage(req, resp);
            case ONLY_SCORE -> showScore(req, resp);
            case ONLY_MISTAKES -> showOnlyMistakesResult(req, resp);
            case MISTAKES_AND_CORRECTS -> showFullResult(req, resp);
        }
    }

    private void showThanksPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/results/thanks_page.jsp").forward(req, resp);
    }

    private void showScore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/results/only_score.jsp").forward(req, resp);
    }

    private void showOnlyMistakesResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/results/only_mistakes.jsp").forward(req, resp);
    }

    private void showFullResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/results/full.jsp").forward(req, resp);
    }
}
