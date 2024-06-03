package org.achareh.service.impl;

import org.achareh.base.service.impl.BaseServiceImpl;
import org.achareh.model.user.Admin;
import org.achareh.repository.AdminRepository;
import org.achareh.service.AdminService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class AdminServiceImpl
        extends BaseServiceImpl<Admin, Long, AdminRepository>
        implements AdminService {
    public AdminServiceImpl(AdminRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Optional<Admin> findByEmailAndPassword(String email, String password) {
        Transaction transaction = null;

        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            Optional<Admin> byEmailAndPassword = repository.findByEmailAndPassword(email, password);

            transaction.commit();

            return byEmailAndPassword;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("An error occurred while signIn: " + e.getMessage());
        }
        return Optional.empty();
    }
}
