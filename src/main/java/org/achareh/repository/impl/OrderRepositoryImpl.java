package org.achareh.repository.impl;

import org.achareh.base.repository.impl.BaseRepositoryImpl;
import org.achareh.model.order.Orders;
import org.achareh.repository.OrderRepository;
import org.hibernate.SessionFactory;


public class OrderRepositoryImpl extends BaseRepositoryImpl<Orders, Long>
        implements OrderRepository {
    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Orders> getEntityClass() {
        return Orders.class;
    }
}