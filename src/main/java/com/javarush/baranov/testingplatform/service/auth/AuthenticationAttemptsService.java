package com.javarush.baranov.testingplatform.service.auth;

import com.javarush.baranov.testingplatform.dao.AuthenticationAttemptsDao;
import com.javarush.baranov.testingplatform.entity.AuthenticationAttempts;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import static com.javarush.baranov.testingplatform.constants.Configuration.BAN_TIME;
import static com.javarush.baranov.testingplatform.constants.Configuration.MAX_AUTH_ATTEMPTS;

@RequiredArgsConstructor
public class AuthenticationAttemptsService {
    private final AuthenticationAttemptsDao authenticationAttemptsDao;

    public boolean isBlocked(String login) {
        AuthenticationAttempts authAttempts = authenticationAttemptsDao.getAuthenticationAttempts(login);

        int currentAttempt = authAttempts.getAttempts() + 1;
        authAttempts.setAttempts(currentAttempt);

        if (currentAttempt > MAX_AUTH_ATTEMPTS && !blockTimeIsFinished(authAttempts.getLastAttempt())) {
            return true;
        } else if (currentAttempt >= MAX_AUTH_ATTEMPTS && blockTimeIsFinished(authAttempts.getLastAttempt())) {
            authAttempts.setAttempts(1);
        }

        authenticationAttemptsDao.updateAttempts(authAttempts);
        return false;
    }

    public void resetAttempts(String login) {
        authenticationAttemptsDao.resetAttempts(login);
    }

    private boolean blockTimeIsFinished(LocalDateTime blockedAt) {
        return blockedAt.plus(BAN_TIME).isBefore(LocalDateTime.now());
    }
}
