package com.javarush.baranov.testingplatform.servlet.student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/student/home")
public class StudentHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/student/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String choice = req.getParameter("choice");

        if ("go_to_test".equals(choice)) {
            resp.sendRedirect(req.getContextPath() + "/student/test/" + req.getParameter("test_id"));
        } else if ("solved_tests".equals(choice)) {
            resp.sendRedirect(req.getContextPath() + "/student/solved");
        }
    }
}
