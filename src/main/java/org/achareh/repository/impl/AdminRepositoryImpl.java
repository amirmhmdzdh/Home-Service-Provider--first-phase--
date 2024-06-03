package org.achareh.repository.impl;

import org.achareh.base.repository.impl.BaseRepositoryImpl;
import org.achareh.connection.SessionFactorySingleton;
import org.achareh.model.user.Admin;
import org.achareh.repository.AdminRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class AdminRepositoryImpl extends BaseRepositoryImpl<Admin, Long>
        implements AdminRepository {


    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Admin> getEntityClass() {
        return Admin.class;
    }

    @Override
    public Optional<Admin> findByEmailAndPassword(String email, String password) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "SELECT a FROM Admin a WHERE a.email = :e AND a.password = :p";
        Query<Admin> query = session.createQuery(hql, Admin.class);
        query.setParameter("e", email);
        query.setParameter("p", password);

        return Optional.ofNullable(query.uniqueResult());
    }
}
