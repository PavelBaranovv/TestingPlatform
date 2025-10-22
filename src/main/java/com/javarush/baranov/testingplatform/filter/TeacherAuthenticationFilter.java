package com.javarush.baranov.testingplatform.filter;

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

@WebFilter(urlPatterns = "/teacher/*")
public class TeacherAuthenticationFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error_message", "Вы не авторизованы");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        } else if (user.getRole() != Role.TEACHER) {
            if (user.getRole() == Role.STUDENT) {
                response.sendRedirect(request.getContextPath() + "/student/home");
                return;
            }
            //TODO: обработка других случаев (перенаправление на страницу ошибки)
        }

        chain.doFilter(req, res);
    }
}