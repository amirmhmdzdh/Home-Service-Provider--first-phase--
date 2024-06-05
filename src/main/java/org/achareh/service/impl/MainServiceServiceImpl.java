package org.achareh.service.impl;

import org.achareh.base.service.impl.BaseServiceImpl;
import org.achareh.model.service.MainService;
import org.achareh.repository.MainServiceRepository;
import org.achareh.service.MainServiceService;
import org.hibernate.SessionFactory;

public class MainServiceServiceImpl
        extends BaseServiceImpl<MainService, Long, MainServiceRepository>
        implements MainServiceService {
    public MainServiceServiceImpl(MainServiceRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
