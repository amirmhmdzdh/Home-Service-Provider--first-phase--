package org.achareh.service.impl;

import org.achareh.base.service.impl.BaseServiceImpl;
import org.achareh.model.offer.Offer;
import org.achareh.repository.OfferRepository;
import org.achareh.service.OfferService;
import org.hibernate.SessionFactory;

public class OfferServiceImpl
        extends BaseServiceImpl<Offer, Long, OfferRepository>
        implements OfferService {
    public OfferServiceImpl(OfferRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
