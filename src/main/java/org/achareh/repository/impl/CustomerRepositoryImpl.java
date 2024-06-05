package org.achareh.repository.impl;

import org.achareh.base.repository.impl.BaseRepositoryImpl;
import org.achareh.connection.SessionFactorySingleton;
import org.achareh.model.user.Customer;
import org.achareh.repository.CustomerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class CustomerRepositoryImpl extends BaseRepositoryImpl<Customer, Long>
        implements CustomerRepository {
    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

    @Override
    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "SELECT c FROM Customer c WHERE c.email =:e AND c.password =:p";
        Query<Customer> query = session.createQuery(hql, Customer.class);
        query.setParameter("e", email);
        query.setParameter("p", password);

        return Optional.ofNullable(query.uniqueResult());
    }
}
