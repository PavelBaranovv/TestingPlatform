package com.javarush.baranov.testingplatform.constants;

import java.time.Duration;

public interface Configuration {
    public static final int MAX_AUTH_ATTEMPTS = 3;
    public static final Duration BAN_TIME = Duration.ofSeconds(20);
}