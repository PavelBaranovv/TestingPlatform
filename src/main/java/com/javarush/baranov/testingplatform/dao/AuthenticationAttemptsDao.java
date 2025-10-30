package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.AuthenticationAttempts;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuthenticationAttemptsDao {

    private final SessionFactory sessionFactory;

    public AuthenticationAttempts getAuthenticationAttempts(String login) {

        final String hql = "FROM AuthenticationAttempts a WHERE a.login = :login";

        try (Session session = sessionFactory.openSession()) {
            AuthenticationAttempts attempts = session.createQuery(hql, AuthenticationAttempts.class)
                    .setParameter("login", login)
                    .uniqueResult();
            if (attempts == null) {
                attempts = resetAttempts(login);
            }
            return attempts;
        }
    }


    public void updateAttempts(AuthenticationAttempts attempts) {
        sessionFactory.inSession((session) -> session.merge(attempts));
    }

    public AuthenticationAttempts resetAttempts(String login) {
        AuthenticationAttempts attempts = new AuthenticationAttempts(login, 0, LocalDateTime.now());
        updateAttempts(attempts);

        return attempts;
    }
}