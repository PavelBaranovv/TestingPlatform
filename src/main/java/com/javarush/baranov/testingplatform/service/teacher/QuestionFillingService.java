package com.javarush.baranov.testingplatform.service.teacher;

import com.javarush.baranov.testingplatform.dao.QuestionDao;
import com.javarush.baranov.testingplatform.dao.TestDao;
import com.javarush.baranov.testingplatform.entity.tests.AnswerOption;
import com.javarush.baranov.testingplatform.entity.tests.Question;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.enums.TestCreationStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class QuestionFillingService {

    private final QuestionDao questionDao;
    private final TestDao testDao;

    public boolean isAnswersCountUpdated(HttpServletRequest req) {
        String action = req.getParameter("action");
        if (action == null) return false;

        HttpSession session = req.getSession();
        Integer answerCount = (Integer) session.getAttribute("answers_count");

        if ("addAnswer".equals(action)) {
            session.setAttribute("answers_count", Math.min(10, answerCount + 1));
            return true;
        } else if ("removeAnswer".equals(action)) {
            session.setAttribute("answers_count", Math.max(1, answerCount - 1));
            return true;
        }
        return false;
    }

    public void addQuestion(HttpServletRequest req) {
        Question question = extractQuestion(req);

        questionDao.save(question);

        incrementQuestionCount(req.getSession());
    }

    public boolean isFinished(HttpServletRequest req) {
        String action = req.getParameter("action");
        if (action != null && action.equals("finish")) {
            HttpSession session = req.getSession();
            updateTestStatus(session);
            return true;
        }
        return false;
    }

    private Question extractQuestion(HttpServletRequest req) {
        String questionText = req.getParameter("questionText");
        Test test = (Test) req.getSession().getAttribute("creating_test");

        Question question = new Question(questionText, test);

        List<AnswerOption> answerOptions = parseAnswerOptions(req, question);
        question.setAnswerOptions(answerOptions);
        return question;
    }

    private List<AnswerOption> parseAnswerOptions(HttpServletRequest req, Question question) {
        int correctAnswer = Integer.parseInt(req.getParameter("correctAnswer"));
        int answersCount = (int) req.getSession().getAttribute("answers_count");

        List<AnswerOption> answerOptions = new ArrayList<>();
        for (int i = 0; i < answersCount; i++) {
            String answerText = req.getParameter("answer_" + i);
            AnswerOption answerOption = new AnswerOption(answerText, i == correctAnswer);
            answerOption.setQuestion(question);
            answerOptions.add(answerOption);
        }
        return answerOptions;
    }

    private void updateTestStatus(HttpSession session) {
        Test test = (Test) session.getAttribute("creating_test");
        test.setStatus(TestCreationStatus.FILLING_SETTINGS);
    }

    private void incrementQuestionCount(HttpSession session) {
        Test test = (Test) session.getAttribute("creating_test");
        int questionCount = test.getQuestionCount();
        test.setQuestionCount(questionCount + 1);
        testDao.update(test);
    }
}