package com.javarush.baranov.testingplatform.service.student;

import com.javarush.baranov.testingplatform.dao.StudentAttemptDao;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.*;
import com.javarush.baranov.testingplatform.util.entities.AttemptAttributes;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class StudentAttemptService {

    private final StudentAttemptDao attemptDao;

    public StudentAttempt createAttempt(User student, Test test) {
        StudentAttempt attempt = new StudentAttempt(student, test, LocalDateTime.now());
        attemptDao.save(attempt);
        return attempt;
    }

    public StudentAttempt fillAttempt(StudentAttempt attempt, AttemptAttributes attributes, Test test) {
        attempt.setCompletedAt(attributes.completedAt());

        Map<Long, Long> questionIdToAnswerId = attributes.questionToAnswer();

        List<StudentAnswer> userAnswers = getUserAnswers(attempt, test, questionIdToAnswerId);
        attempt.setStudentAnswers(userAnswers);

        calculateResults(attempt, test.getNeedToAnswer());

        attemptDao.update(attempt);

        return attempt;
    }

    private static List<StudentAnswer> getUserAnswers(StudentAttempt attempt, Test test, Map<Long, Long> questionIdToAnswerId) {
        List<StudentAnswer> answers = new ArrayList<>();

        for (Question question : test.getQuestions()) {
            for (AnswerOption answerOption : question.getAnswerOptions()) {
                if (questionIdToAnswerId.get(question.getId()).equals(answerOption.getId())) {
                    answers.add(new StudentAnswer(attempt, question, answerOption, answerOption.getIsCorrect()));
                }
            }
        }

        return answers;
    }

    private void calculateResults(StudentAttempt attempt, Integer needToAnswer) {
        List<StudentAnswer> answers = attempt.getStudentAnswers();

        long score = answers.stream().filter(StudentAnswer::getIsCorrect).count();

        attempt.setScore(score);
        attempt.setSuccess(score >= needToAnswer);
    }
}
