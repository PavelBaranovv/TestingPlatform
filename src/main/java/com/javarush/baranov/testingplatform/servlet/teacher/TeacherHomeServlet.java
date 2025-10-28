package com.javarush.baranov.testingplatform.servlet.teacher;

import com.javarush.baranov.testingplatform.service.teacher.TeacherHomeService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/teacher/home")
public class TeacherHomeServlet extends HttpServlet {

    private TeacherHomeService teacherHomeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        teacherHomeService = (TeacherHomeService) context.getAttribute("teacherHomeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tests", teacherHomeService.getCreatedTests(req));
        req.getRequestDispatcher("/teacher/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        teacherHomeService.processChoice(req, resp);
    }
}
