package com.javarush.baranov.testingplatform.servlet.student;
import com.javarush.baranov.testingplatform.constants.Route;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.javarush.baranov.testingplatform.constants.WebResource.STUDENT_HOME_PAGE_JSP;

@WebServlet(urlPatterns = Route.STUDENT_HOME)
public class StudentHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(STUDENT_HOME_PAGE_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String choice = req.getParameter("choice");

        if ("go_to_test".equals(choice)) {
            resp.sendRedirect(req.getContextPath() + Route.STUDENT_TEST + "/" + req.getParameter("test_id"));
        } else if ("solved_tests".equals(choice)) {
            resp.sendRedirect(req.getContextPath() + Route.STUDENT_SOLVED);
        }
    }
}
