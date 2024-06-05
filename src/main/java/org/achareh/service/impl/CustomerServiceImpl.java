package org.achareh.service.impl;

import org.achareh.base.service.impl.BaseServiceImpl;
import org.achareh.model.user.Customer;
import org.achareh.repository.CustomerRepository;
import org.achareh.service.CustomerService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

public class CustomerServiceImpl
        extends BaseServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {
    public CustomerServiceImpl(CustomerRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
    @Override
    public Optional<Customer> findByEmailAndPassword(String email, String password) {

        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            Optional<Customer> byEmailAndPassword = repository.findByEmailAndPassword(email, password);
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
