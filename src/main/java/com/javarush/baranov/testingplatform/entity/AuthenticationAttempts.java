package com.javarush.baranov.testingplatform.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthenticationAttempts {
    private final String login;
    private final int attempts;
    private final LocalDateTime lastAttempt;
}
