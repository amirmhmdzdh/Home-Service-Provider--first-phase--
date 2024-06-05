package org.achareh.repository.impl;

import org.achareh.base.repository.impl.BaseRepositoryImpl;
import org.achareh.model.service.MainService;
import org.achareh.repository.MainServiceRepository;
import org.hibernate.SessionFactory;
public class MainServiceRepositoryImpl extends BaseRepositoryImpl<MainService,Long>
implements MainServiceRepository {
    public MainServiceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<MainService> getEntityClass() {
        return MainService.class;
    }
}
