package com.javarush.baranov.testingplatform.util;

import com.javarush.baranov.testingplatform.enums.TestShowResult;
import com.javarush.baranov.testingplatform.util.entities.TestSettings;
import jakarta.servlet.http.HttpServletRequest;

public class TestSettingsExtractor {

    public TestSettings extract(HttpServletRequest req) {
        String needToAnswerStr = req.getParameter("need_to_answer");
        String showResultStr = req.getParameter("show_result");

        if (needToAnswerStr == null || showResultStr == null) {
            throw new NullPointerException("Cannot extract settings from request");
        }

        int needToAnswer = Integer.parseInt(needToAnswerStr);
        TestShowResult showResult = TestShowResult.valueOf(showResultStr);

        return new TestSettings(needToAnswer, showResult);
    }
}
