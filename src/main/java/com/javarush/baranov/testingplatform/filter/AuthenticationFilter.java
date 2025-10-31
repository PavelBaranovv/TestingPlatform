package com.javarush.baranov.testingplatform.filter;

import com.javarush.baranov.testingplatform.constants.Route;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.enums.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {Route.STUDENT + "/*", Route.TEACHER + "/*"})
public class AuthenticationFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error_message", "Вы не авторизованы");
            response.sendRedirect(request.getContextPath() + Route.LOGIN);
            return;
        }

        if (!validateRoute(user.getRole(), request, response)) {
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean validateRoute(Role role, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();

        if (Role.STUDENT.equals(role) && !uri.contains(Route.STUDENT)) {
            resp.sendRedirect(req.getContextPath() + Route.STUDENT_HOME);
            return false;
        } else if (Role.TEACHER.equals(role) && !uri.contains(Route.TEACHER)) {
            resp.sendRedirect(req.getContextPath() + Route.TEACHER_HOME);
            return false;
        }

        return true;
    }
}