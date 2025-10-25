package com.javarush.baranov.testingplatform.service.teacher;

import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.service.TestService;
import com.javarush.baranov.testingplatform.util.BaseTestAttributesExtractor;
import com.javarush.baranov.testingplatform.util.TestSettingsExtractor;
import com.javarush.baranov.testingplatform.util.entities.BaseTestAttributes;
import com.javarush.baranov.testingplatform.util.entities.TestSettings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestCreationService {

    private final TestService testService;
    private final BaseTestAttributesExtractor baseAttributesExtractor;
    private final TestSettingsExtractor settingsExtractor;

    public void createTest(HttpServletRequest req) {
        BaseTestAttributes baseAttributes = baseAttributesExtractor.extract(req);
        Test test = testService.createTest(baseAttributes);

        req.getSession().setAttribute("creating_test", test);
    }

    public void applySettings(HttpServletRequest req) {
        TestSettings testSettings = settingsExtractor.extract(req);
        Test test = (Test) req.getSession().getAttribute("creating_test");
        testService.updateTestSettings(test, testSettings);
    }

    public void clean(HttpSession session) {
        session.removeAttribute("creating_test");
    }
}
