package com.javarush.baranov.testingplatform.util.entities;

import com.javarush.baranov.testingplatform.enums.TestResultView;

public record TestSettings(
        int needToAnswer,
        TestResultView resultView
) {}
