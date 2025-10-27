package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.tests.Question;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public class QuestionDao {

    private final SessionFactory sessionFactory;

    public void save(Question question) {
        sessionFactory.inTransaction((session) -> {
            session.persist(question);
            session.flush();
        });
    }
}
