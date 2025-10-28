package com.javarush.baranov.testingplatform.dao;

import com.javarush.baranov.testingplatform.entity.tests.StudentAnswer;
import com.javarush.baranov.testingplatform.entity.tests.StudentAttempt;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public class StudentAttemptDao {

    private final SessionFactory sessionFactory;

    public StudentAttempt getAttemptWithAnswers(Long id) {
        try (Session session = sessionFactory.openSession()) {
            StudentAttempt attempt = session.createQuery("""
                            select a
                            from StudentAttempt a
                            left join fetch a.studentAnswers ans
                            left join fetch a.user
                            where a.id = :id
                            """, StudentAttempt.class)
                    .setParameter("id", id)
                    .getSingleResult();

            session.createQuery("""
                            select ans
                            from StudentAnswer ans
                            left join fetch ans.question q
                            left join fetch q.answerOptions
                            join ans.attempt a
                            where a.id = :id
                            """, StudentAnswer.class)
                    .setParameter("id", id)
                    .getResultList();

            return attempt;
        }
    }

    public void save(StudentAttempt attempt) {
        sessionFactory.inTransaction((session) -> session.persist(attempt));
    }

    public void update(StudentAttempt attempt) {
        sessionFactory.inTransaction(session -> session.merge(attempt));
    }
}
