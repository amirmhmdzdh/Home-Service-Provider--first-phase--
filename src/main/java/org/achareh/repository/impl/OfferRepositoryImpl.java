package org.achareh.repository.impl;

import org.achareh.base.repository.impl.BaseRepositoryImpl;
import org.achareh.model.offer.Offer;
import org.achareh.repository.OfferRepository;
import org.hibernate.SessionFactory;

public class OfferRepositoryImpl extends BaseRepositoryImpl<Offer, Long>
        implements OfferRepository {
    public OfferRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Offer> getEntityClass() {
        return Offer.class;
    }
}
