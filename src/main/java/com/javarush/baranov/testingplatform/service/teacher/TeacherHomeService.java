package com.javarush.baranov.testingplatform.service.teacher;

import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class TeacherHomeService {
    private final TestService testService;

    public void setCreatedTestsAttribute(HttpServletRequest req) {
        List<Test> tests = getCreatedTests(req);
        req.setAttribute("tests", tests);
    }

    public void processChoice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String choice = req.getParameter("choice");
        switch (choice) {
            case "create_test" -> resp.sendRedirect(req.getContextPath() + "/teacher/create-test/new");
            case "results" -> {
                String testId = getTestId(req);
                resp.sendRedirect(req.getContextPath() + "/teacher/results/" + testId);
            }
            case "delete_test" -> {
                String testId = getTestId(req);
                resp.sendRedirect(req.getContextPath() + "/teacher/delete-test/" + testId);
            }
            case null, default -> resp.sendRedirect(req.getContextPath() + "teacher/home");
        }
    }

    private List<Test> getCreatedTests(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        return testService.getTestsByCreator(user);
    }

    private String getTestId(HttpServletRequest req) {
        String test_id = req.getParameter("test_id");
        if (test_id == null) throw new IllegalStateException("test_id is null");
        return test_id;
    }
}
