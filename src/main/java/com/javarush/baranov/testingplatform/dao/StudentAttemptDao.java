package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.tests.StudentAttempt;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public class StudentAttemptDao {

    private final SessionFactory sessionFactory;

    public void save(StudentAttempt attempt) {
        sessionFactory.inTransaction((session) -> session.persist(attempt));
    }

    public void update(StudentAttempt attempt) {
        sessionFactory.inTransaction(session -> session.merge(attempt));
    }
}
