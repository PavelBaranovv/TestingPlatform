package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class UserDao {

    private final SessionFactory sessionFactory;

    public void save(User user) {
        sessionFactory.inTransaction((session) -> session.persist(user));
    }

    public Optional<User> findByLogin(String login) {
        String hql = "from User u where u.login = :login";

        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery(hql, User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            return Optional.ofNullable(user);
        }
    }
}
