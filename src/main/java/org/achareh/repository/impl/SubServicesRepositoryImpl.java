package org.achareh.repository.impl;

import org.achareh.base.repository.impl.BaseRepositoryImpl;
import org.achareh.connection.SessionFactorySingleton;
import org.achareh.model.service.SubService;
import org.achareh.repository.SubServicesRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class SubServicesRepositoryImpl extends BaseRepositoryImpl<SubService, Long>
        implements SubServicesRepository {
    public SubServicesRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<SubService> getEntityClass() {
        return SubService.class;
    }


    @Override
    public List<SubService> getSubServicesByMainServiceId(Long mainServiceId) {
        Session session = SessionFactorySingleton.getInstance().getCurrentSession();

        String hql = "FROM SubService WHERE mainService.id = :mainServiceId";
        List<SubService> subServices = session.createQuery(hql, SubService.class)
                .setParameter("mainServiceId", mainServiceId)
                .getResultList();
        return subServices;
    }
}