package com.javarush.baranov.testingplatform.servlet.auth;

import com.javarush.baranov.testingplatform.service.auth.AuthenticationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {

    private AuthenticationService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        authService = (AuthenticationService) context.getAttribute("authenticationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!authService.register(req)) {
            req.setAttribute("registration_error", "Такой аккаунт уже существует");
            req.getRequestDispatcher("/registration.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/login");
    }
}
