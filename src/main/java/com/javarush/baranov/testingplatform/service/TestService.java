package com.javarush.baranov.testingplatform.service;

import com.javarush.baranov.testingplatform.dao.TestDao;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.enums.TestCreationStatus;
import com.javarush.baranov.testingplatform.util.entities.BaseTestAttributes;
import com.javarush.baranov.testingplatform.util.entities.TestSettings;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TestService {

    private final TestDao testDao;

    public Test createTest(BaseTestAttributes baseTestAttributes) {
        String name = baseTestAttributes.name();
        String description = baseTestAttributes.description();
        TestCreationStatus status = TestCreationStatus.QUESTION_FILLING;
        User createdBy = baseTestAttributes.createdBy();

        Test test = new Test(name, description, status, createdBy);
        testDao.save(test);

        return test;
    }

    public void updateTestSettings(Test test, TestSettings testSettings) {
        test.setNeedToAnswer(testSettings.needToAnswer());
        test.setShowResult(testSettings.showResult());
        test.setCreatedAt(LocalDateTime.now());
        test.setStatus(TestCreationStatus.CREATED);
        testDao.update(test);
    }

    public List<Test> getCreatingTests(User user) {
        return testDao.getCreatingTests(user);
    }


}
