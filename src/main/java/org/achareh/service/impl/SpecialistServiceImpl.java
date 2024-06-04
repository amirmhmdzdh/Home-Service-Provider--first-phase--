package org.achareh.service.impl;

import org.achareh.base.service.impl.BaseServiceImpl;
import org.achareh.model.user.Specialist;
import org.achareh.repository.SpecialistRepository;
import org.achareh.service.SpecialistService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class SpecialistServiceImpl
        extends BaseServiceImpl<Specialist, Long, SpecialistRepository>
        implements SpecialistService {
    public SpecialistServiceImpl(SpecialistRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Optional<Specialist> findByEmailAndPassword(String email, String password) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<Specialist> byEmailAndPassword = repository.findByEmailAndPassword(email, password);
            transaction.commit();
            session.close();
            return byEmailAndPassword;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("An error occurred while signIn :  " + e.getMessage());
        }
        return Optional.empty();
    }
}