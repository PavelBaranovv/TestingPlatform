package com.javarush.baranov.testingplatform.service.teacher;

import com.javarush.baranov.testingplatform.dao.TestDao;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.util.TestIdExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttemptsViewService {

    private final TestDao testDao;
    private final TestIdExtractor idExtractor;

    public void setTestAttribute(HttpServletRequest req) {
        String testId = idExtractor.extract(req.getRequestURI());
        Test test = testDao.getTestWithAttemptsAndUsers(testId);
        req.setAttribute("test", test);
    }
}
