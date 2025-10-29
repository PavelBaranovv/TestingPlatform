package com.javarush.baranov.testingplatform.servlet.teacher;

import com.javarush.baranov.testingplatform.constants.Route;
import com.javarush.baranov.testingplatform.constants.WebResource;
import com.javarush.baranov.testingplatform.service.TestService;
import com.javarush.baranov.testingplatform.util.TestIdExtractor;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = Route.DELETE_TEST + "/*")
public class TestDeleteServlet extends HttpServlet {

    private TestService testService;
    private TestIdExtractor testIdExtractor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        testService = (TestService) context.getAttribute("testService");
        testIdExtractor = (TestIdExtractor) context.getAttribute("testIdExtractor");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WebResource.TEST_DELETE_CONFIRM_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choice = req.getParameter("choice");
        if ("confirm".equals(choice)) {
            testService.deteteTest(testIdExtractor.extract(req.getRequestURI()));
        }
        resp.sendRedirect(req.getContextPath() + Route.TEACHER_HOME);
    }
}
