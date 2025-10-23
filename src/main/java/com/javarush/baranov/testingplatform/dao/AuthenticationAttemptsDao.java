package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.AuthenticationAttempts;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuthenticationAttemptsDao {

    private final SessionFactory sessionFactory;

    public AuthenticationAttempts getAuthenticationAttempts(String login) {

        String hql = "FROM AuthenticationAttempts a WHERE a.login = :login";

        return sessionFactory.fromTransaction(session -> {
            Query<AuthenticationAttempts> query = session.createQuery(hql, AuthenticationAttempts.class);
            query.setParameter("login", login);
            AuthenticationAttempts attempts = query.uniqueResult();

            if (attempts == null) {
                attempts = new AuthenticationAttempts(login, 0, LocalDateTime.now());
                session.persist(attempts);
            }

            return attempts;
        });

    }

    public void updateAttempts(String login, int attempts, LocalDateTime lastAttempt) {

        String hql = "FROM AuthenticationAttempts a WHERE a.login = :login";

        sessionFactory.inTransaction(session -> {
            Query<AuthenticationAttempts> query = session.createQuery(
                    hql, AuthenticationAttempts.class);
            query.setParameter("login", login);
            AuthenticationAttempts authAttempts = query.uniqueResult();

            if (authAttempts != null) {
                authAttempts.setAttempts(attempts);
                authAttempts.setLastAttempt(lastAttempt);
                session.merge(authAttempts);
            } else {
                authAttempts = new AuthenticationAttempts(login, attempts, lastAttempt);
                session.persist(authAttempts);
            }
        });
    }

    public void resetAttempts(String login) {

        String hql = "FROM AuthenticationAttempts a WHERE a.login = :login";

        sessionFactory.inTransaction(session -> {
            Query<AuthenticationAttempts> query = session.createQuery(
                    hql, AuthenticationAttempts.class);
            query.setParameter("login", login);
            AuthenticationAttempts authAttempts = query.uniqueResult();

            if (authAttempts != null) {
                authAttempts.setAttempts(0);
                authAttempts.setLastAttempt(LocalDateTime.now());
                session.merge(authAttempts);
            }
        });
    }
}