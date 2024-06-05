package org.achareh.service.impl;

import org.achareh.base.service.impl.BaseServiceImpl;
import org.achareh.model.order.Orders;
import org.achareh.repository.OrderRepository;
import org.achareh.service.OrderService;
import org.hibernate.SessionFactory;

public class OrderServiceImpl
        extends BaseServiceImpl<Orders, Long, OrderRepository>
        implements OrderService {
    public OrderServiceImpl(OrderRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
