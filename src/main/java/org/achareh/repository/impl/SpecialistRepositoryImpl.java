package org.achareh.repository.impl;

import org.achareh.base.repository.impl.BaseRepositoryImpl;
import org.achareh.connection.SessionFactorySingleton;
import org.achareh.model.user.Specialist;
import org.achareh.repository.SpecialistRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Optional;

public class SpecialistRepositoryImpl extends BaseRepositoryImpl<Specialist, Long>
        implements SpecialistRepository {
    public SpecialistRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Specialist> getEntityClass() {
        return Specialist.class;
    }


    @Override
    public Optional<Specialist> findByEmailAndPassword(String email, String password) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "SELECT s FROM Specialist s WHERE s.email =:e AND s.password =:p ";
        Query<Specialist> query = session.createQuery(hql, Specialist.class);
        query.setParameter("e", email);
        query.setParameter("p", password);

        return Optional.ofNullable(query.uniqueResult());
    }
}
