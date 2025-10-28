package com.javarush.baranov.testingplatform.servlet.teacher;

import com.javarush.baranov.testingplatform.service.teacher.AttemptsViewService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/teacher/results/*")
public class AttemptsViewServlet extends HttpServlet {

    private AttemptsViewService attemptsViewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        attemptsViewService = (AttemptsViewService) context.getAttribute("attemptsViewService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        attemptsViewService.setTestAttribute(req);
        req.getRequestDispatcher("/teacher/test_attempts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        attemptsViewService.processChoice(req, resp);
    }
}
