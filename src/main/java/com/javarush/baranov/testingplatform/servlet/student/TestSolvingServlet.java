package com.javarush.baranov.testingplatform.servlet.student;

import com.javarush.baranov.testingplatform.service.student.TestSolvingService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/student/test/*")
public class TestSolvingServlet extends HttpServlet {

    private TestSolvingService solvingService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        solvingService = (TestSolvingService) context.getAttribute("testSolvingService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        solvingService.navigate(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        solvingService.processChoice(req, resp);
    }
}
