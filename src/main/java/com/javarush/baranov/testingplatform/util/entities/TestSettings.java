package com.javarush.baranov.testingplatform.util.entities;

import com.javarush.baranov.testingplatform.enums.TestShowResult;

public record TestSettings(
        int needToAnswer,
        TestShowResult showResult
) {}
