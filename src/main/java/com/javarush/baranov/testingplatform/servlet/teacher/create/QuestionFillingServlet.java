package com.javarush.baranov.testingplatform.servlet.teacher.create;

import com.javarush.baranov.testingplatform.constants.Configuration;
import com.javarush.baranov.testingplatform.service.teacher.QuestionFillingService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/teacher/create-test/questions")
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
        req.getSession().setAttribute("answers_count", Configuration.DEFAULT_ANSWERS_COUNT);
        req.getRequestDispatcher("/teacher/question_fill.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (questionFillingService.isAnswersCountUpdated(req)) {
            req.getRequestDispatcher("/teacher/question_fill.jsp").forward(req, resp);
            return;
        }

        questionFillingService.addQuestion(req);

        if (questionFillingService.isFinished(req)) {
            resp.sendRedirect(req.getContextPath() + "/teacher/create-test/settings");
        } else {
            resp.sendRedirect(req.getContextPath() + "/teacher/create-test/questions");
        }
    }
}
