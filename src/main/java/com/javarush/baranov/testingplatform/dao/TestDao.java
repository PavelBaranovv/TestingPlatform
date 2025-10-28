package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.User;
import com.javarush.baranov.testingplatform.entity.tests.Question;
import com.javarush.baranov.testingplatform.entity.tests.Test;
import com.javarush.baranov.testingplatform.enums.TestCreationStatus;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

@RequiredArgsConstructor
public class TestDao {

    private final SessionFactory sessionFactory;

    public void save(Test test) {
        sessionFactory.inTransaction((session) -> session.persist(test));
    }

    public Test getTestWithQuestions(String id) {
        try (Session session = sessionFactory.openSession()) {
            Test test = session.createQuery("""
                select t
                from Test t
                left join fetch t.questions q
                where t.id = :id""", Test.class)
                    .setParameter("id", id)
                    .uniqueResult();

            if (test == null) return null;

            session.createQuery("""
                select q
                from Question q
                left join fetch q.answerOptions a
                join q.test t
                where t.id = :id""", Question.class)
                    .setParameter("id", id)
                    .getResultList();

            return test;
        }
    }

    public void update(Test test) {
        sessionFactory.inTransaction(session -> session.merge(test));
    }

    public List<Test> getUserTests(User user, TestCreationStatus status) {
        String hql = "FROM Test t WHERE t.createdBy = :user AND t.status = :status";

        return sessionFactory.fromTransaction(session -> {
            Query<Test> query = session.createQuery(hql, Test.class);
            query.setParameter("user", user);
            query.setParameter("status", status);
            return query.getResultList();
        });
    }

    public List<Test> getCreatingTests(User user) {
        String hql = "FROM Test t WHERE t.createdBy = :user AND t.status != :status";

        return sessionFactory.fromTransaction(session -> {
            Query<Test> query = session.createQuery(hql, Test.class);
            query.setParameter("user", user);
            query.setParameter("status", TestCreationStatus.CREATED);
            return query.getResultList();
        });
    }

    public void deleteTest(String id) {
        sessionFactory.inTransaction(session -> {
            Test test = session.get(Test.class, id);
            if (test != null) {
                session.remove(test);
            }
        });
    }
}
