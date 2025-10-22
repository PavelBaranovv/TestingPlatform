package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.AuthenticationAttempts;

import java.time.LocalDateTime;

public class AuthenticationAttemptsDao {
    public AuthenticationAttempts getAuthenticationAttempts(String login) {
        //TODO: AuthenticationAttemptsDao::getAttempt
        return new AuthenticationAttempts("login", 2, LocalDateTime.now());
    }

    public void updateAttempts(String login, int attempts, LocalDateTime now) {
        //TODO: AuthenticationAttemptsDao::updateAttempts
        System.out.println("Set attempts " + attempts + " to login " + login + " at " + now);
    }
}
