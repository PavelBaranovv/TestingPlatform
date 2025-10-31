package com.javarush.baranov.testingplatform.servlet.teacher.create;

import com.javarush.baranov.testingplatform.constants.AppSettings;
import com.javarush.baranov.testingplatform.constants.Route;
import com.javarush.baranov.testingplatform.constants.WebResource;
import com.javarush.baranov.testingplatform.service.teacher.QuestionFillingService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = Route.TEACHER_CREATE_TEST_QUESTIONS)
public class QuestionFillingServlet extends HttpServlet {

    private QuestionFillingService questionFillingService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        questionFillingService = (QuestionFillingService) context.getAttribute("questionFillingService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("answers_count", AppSettings.DEFAULT_ANSWERS_COUNT);
        req.getRequestDispatcher(WebResource.TEACHER_QUESTION_FILLING_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (questionFillingService.isAnswersCountUpdated(req)) {
            req.getRequestDispatcher(WebResource.TEACHER_QUESTION_FILLING_JSP).forward(req, resp);
            return;
        }

        questionFillingService.addQuestion(req);

        if (questionFillingService.isFinished(req)) {
            resp.sendRedirect(req.getContextPath() + Route.TEACHER_CREATE_TEST_SETTINGS);
        } else {
            resp.sendRedirect(req.getContextPath() + Route.TEACHER_CREATE_TEST_QUESTIONS);
        }
    }
}
