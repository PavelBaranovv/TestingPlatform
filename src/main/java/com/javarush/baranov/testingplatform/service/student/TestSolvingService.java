package com.javarush.baranov.testingplatform.service.student;

import com.javarush.baranov.testingplatform.dao.TestDao;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.StudentAttempt;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.service.StudentAttemptsService;
import com.javarush.baranov.testingplatform.util.AttemptAttributesExtractor;
import com.javarush.baranov.testingplatform.util.entities.AttemptAttributes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class TestSolvingService {

    private final TestDao testDao;
    private final AttemptAttributesExtractor attemptAttributesExtractor;
    private final StudentAttemptsService attemptsService;

    public void navigate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Test test = getTest(req);
        HttpSession session = req.getSession();

        if (test == null) {
            session.setAttribute("id_error", "Теста с таким ID не найдено. Убедитесь, что вы ввели его верно");
            resp.sendRedirect(req.getContextPath() + "/student/home");
            return;
        }

        String status = (String) session.getAttribute("solving_status");

        if ("solving".equals(status)) {
            req.getRequestDispatcher("/student/test_solving.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/student/test_welcome.jsp").forward(req, resp);
        }
    }

    public void processChoice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choice = req.getParameter("choice");

        switch (choice) {
            case "start_test" -> startTest(req, resp);
            case "home" -> redirectToHomePage(req, resp);
            case "finish_test" -> finishTest(req, resp);
            default -> navigate(req, resp);
        }
    }


    private String extractTestIdFromUri(String uri) {
        String[] parts = uri.split("/");
        return parts.length > 0 ? parts[parts.length - 1] : null;
    }

    private Test getTest(HttpServletRequest req) {
        String uri = req.getRequestURI();
        String id = extractTestIdFromUri(uri);
        if (id == null) return null;

        HttpSession session = req.getSession();
        if (session.getAttribute("solving_test") != null) {
            Test test = (Test) session.getAttribute("solving_test");
            if (test.getId().equals(id)) {
                return (Test) session.getAttribute("solving_test");
            }
        }

        Test test = testDao.getTestWithQuestions(id);
        session.setAttribute("solving_test", test);
        return test;
    }

    private void startTest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        Test test = (Test) session.getAttribute("solving_test");

        StudentAttempt attempt = attemptsService.createAttempt(user, test);

        session.setAttribute("attempt", attempt);
        session.setAttribute("solving_status", "solving");
        navigate(req, resp);
    }

    private void finishTest(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        AttemptAttributes attributes = attemptAttributesExtractor.extract(req);

        StudentAttempt attempt = (StudentAttempt) session.getAttribute("attempt");
        User user = (User) session.getAttribute("user");
        Test test = (Test) session.getAttribute("solving_test");

        attempt = attemptsService.fillAttempt(attempt, attributes, test);
        session.setAttribute("attempt", attempt);
    }

    private void redirectToHomePage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        cleanSessionAttributes(req.getSession());
        resp.sendRedirect(req.getContextPath() + "/student/home");
    }

    private static void cleanSessionAttributes(HttpSession session) {
        session.removeAttribute("solving_test");
        session.removeAttribute("solving_status");
        session.removeAttribute("attempt");
    }
}
