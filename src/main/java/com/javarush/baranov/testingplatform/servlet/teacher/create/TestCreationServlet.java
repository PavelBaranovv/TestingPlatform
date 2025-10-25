package com.javarush.baranov.testingplatform.servlet.teacher.create;

import com.javarush.baranov.testingplatform.service.teacher.TestCreationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/teacher/create-test/new")
public class TestCreationServlet extends HttpServlet {

    private TestCreationService creationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        creationService = (TestCreationService) context.getAttribute("testCreationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/teacher/create_new_test.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        creationService.createTest(req);
        resp.sendRedirect(req.getContextPath() + "/teacher/create-test/questions");
    }
}
