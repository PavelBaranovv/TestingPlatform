package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.Credentials;
import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.enums.Role;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

@RequiredArgsConstructor
public class UserDao {

    private final SessionFactory sessionFactory;

    public void save(Credentials credentials) {
        String login = credentials.getLogin();
        String password = credentials.getPassword();
        Role role = credentials.getRole();
        User user = new User(login, password, role);
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
