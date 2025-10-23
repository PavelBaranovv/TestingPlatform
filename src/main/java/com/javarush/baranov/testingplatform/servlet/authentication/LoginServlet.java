package com.javarush.baranov.testingplatform.servlet.authentication;

import com.javarush.baranov.testingplatform.enums.Role;
import com.javarush.baranov.testingplatform.service.AuthenticationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    AuthenticationService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        authService = (AuthenticationService) context.getAttribute("authenticationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = authService.login(req);
        if (role == null) {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else if (role == Role.TEACHER) {
            //TODO: replace static references
            resp.sendRedirect(req.getContextPath() + "/teacher/teacher_home.jsp");
        } else if (role == Role.STUDENT) {
            resp.sendRedirect(req.getContextPath() + "/student/student_home.jsp");
        }
    }
}
