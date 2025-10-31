package com.javarush.baranov.testingplatform.constants;

import java.time.Duration;

public interface AppSettings {
    int MAX_AUTH_ATTEMPTS = 3;
    Duration BAN_TIME = Duration.ofMinutes(5);
    int DEFAULT_ANSWERS_COUNT = 4;
}