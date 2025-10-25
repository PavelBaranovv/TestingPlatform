package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

@RequiredArgsConstructor
public class UserDao {

    private final SessionFactory sessionFactory;

    public void save(User user) {
        sessionFactory.inTransaction((session) -> session.persist(user));
    }

    public Optional<User> findByLogin(String login) {

        String hql = "FROM User u WHERE u.login = :login";

        return sessionFactory.fromTransaction(session -> {
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("login", login);
            return Optional.ofNullable(query.uniqueResult());
        });
    }
}
