package com.javarush.baranov.testingplatform.servlet.student;

import com.javarush.baranov.testingplatform.service.student.SolvedTestsService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/student/solved")
public class SolvedTestsServlet extends HttpServlet {

    private SolvedTestsService solvedTestsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        solvedTestsService = (SolvedTestsService) context.getAttribute("solvedTestsService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        solvedTestsService.setTestsAttribute(req);
        req.getRequestDispatcher("/student/solved.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        solvedTestsService.processChoice(req, resp);
    }
}
