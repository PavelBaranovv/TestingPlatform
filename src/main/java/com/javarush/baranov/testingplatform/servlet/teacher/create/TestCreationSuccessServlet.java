package com.javarush.baranov.testingplatform.servlet.teacher.create;

import com.javarush.baranov.testingplatform.constants.Route;
import com.javarush.baranov.testingplatform.constants.WebResource;
import com.javarush.baranov.testingplatform.service.teacher.TestCreationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = Route.TEACHER_CREATE_TEST_SUCCESS)
public class TestCreationSuccessServlet extends HttpServlet {

    private TestCreationService creationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        creationService = (TestCreationService) context.getAttribute("testCreationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(WebResource.TEST_CREATION_SUCCESS_JSP).forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("action") != null && req.getParameter("action").equals("home")) {
            creationService.clean(req.getSession());
            resp.sendRedirect(Route.TEACHER_HOME);
        }
    }
}
