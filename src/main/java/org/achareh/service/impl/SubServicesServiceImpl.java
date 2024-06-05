package org.achareh.service.impl;

import org.achareh.base.service.impl.BaseServiceImpl;
import org.achareh.model.service.SubService;
import org.achareh.repository.SubServicesRepository;
import org.achareh.service.SubServicesService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SubServicesServiceImpl
        extends BaseServiceImpl<SubService, Long, SubServicesRepository>
        implements SubServicesService {
    public SubServicesServiceImpl(SubServicesRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<SubService> getSubServicesByMainServiceId(Long mainServiceId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();

            List<SubService> subServicesByMainServiceId = repository.getSubServicesByMainServiceId(mainServiceId);

            transaction.commit();
            return subServicesByMainServiceId;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("An error occurred " + e.getMessage());
        }
        return null;
    }
}
