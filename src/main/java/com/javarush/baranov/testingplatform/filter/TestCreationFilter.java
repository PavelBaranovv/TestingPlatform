package com.javarush.baranov.testingplatform.filter;

import com.javarush.baranov.testingplatform.constants.Route;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.enums.TestCreationStatus;
import com.javarush.baranov.testingplatform.service.TestService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@WebFilter(urlPatterns = Route.TEACHER_CREATE_TEST + "/*")
public class TestCreationFilter extends HttpFilter {

    private final ConcurrentHashMap<TestCreationStatus, String> navigationHashMap = new ConcurrentHashMap<>() {{
        put(TestCreationStatus.FILLING_BASE_ATTRIBUTES, Route.TEACHER_CREATE_TEST_NEW);
        put(TestCreationStatus.QUESTION_FILLING, Route.TEACHER_CREATE_TEST_QUESTIONS);
        put(TestCreationStatus.FILLING_SETTINGS, Route.TEACHER_CREATE_TEST_SETTINGS);
        put(TestCreationStatus.CREATED, Route.TEACHER_CREATE_TEST_SUCCESS);
    }};

    private TestService testService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        testService = (TestService) servletContext.getAttribute("testService");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        validateTestAttribute(session);

        if(validateURI(request, response)) {
            chain.doFilter(req, res);
        }
    }

    private void validateTestAttribute(HttpSession session) {
        Test test = (Test) session.getAttribute("creating_test");
        if (test == null) {
            User user = (User) session.getAttribute("user");
            test = getUserCreatingTest(user);
            if (test != null) session.setAttribute("creating_test", test);
        }
    }


    private Test getUserCreatingTest(User user) {
        List<Test> creatingTests = testService.getCreatingTests(user);
        if (creatingTests.isEmpty()) {
            return null;
        }
        return creatingTests.getFirst();
    }

    private boolean validateURI(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Test test = (Test) req.getSession().getAttribute("creating_test");

        String expectedUri = getExpectedUri(test);

        String uri = req.getRequestURI();
        if (!uri.equals(expectedUri)) {
            resp.sendRedirect(req.getContextPath() + expectedUri);
            return false;
        }
        return true;
    }

    private String getExpectedUri(Test test) {
        if (test == null) {
            return Route.TEACHER_CREATE_TEST_NEW;
        }
        return navigationHashMap.get(test.getStatus());
    }
}