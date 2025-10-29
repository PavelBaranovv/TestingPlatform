package com.javarush.baranov.testingplatform.service.student;

import com.javarush.baranov.testingplatform.dao.StudentAttemptDao;
import com.javarush.baranov.testingplatform.dao.TestDao;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.StudentAttempt;
import com.javarush.baranov.testingplatform.enums.TestCreationStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SolvedTestsService {
    private final StudentAttemptDao attemptDao;

    public void setTestsAttribute(HttpServletRequest req) {
        User student = (User) req.getSession().getAttribute("user");
        List<StudentAttempt> attempts = attemptDao.getAttempts(student);
        req.setAttribute("attempts", attempts);
    }
}
