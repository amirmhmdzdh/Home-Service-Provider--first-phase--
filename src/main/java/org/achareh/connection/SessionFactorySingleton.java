package org.achareh.connection;

import org.achareh.model.address.Address;
import org.achareh.model.comment.Comment;
import org.achareh.model.offer.Offer;
import org.achareh.model.order.Orders;
import org.achareh.model.service.MainService;
import org.achareh.model.service.SubService;
import org.achareh.model.user.Admin;
import org.achareh.model.user.Customer;
import org.achareh.model.user.Specialist;
import org.achareh.model.user.Users;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class SessionFactorySingleton {
    private SessionFactorySingleton() {
    }

    private static class SessionFactoryHelper {
        static org.hibernate.service.ServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        private static final SessionFactory INSTANCE =
                new MetadataSources(registry)
                        .addAnnotatedClass(Users.class)
                        .addAnnotatedClass(Address.class)
                        .addAnnotatedClass(Comment.class)
                        .addAnnotatedClass(Offer.class)
                        .addAnnotatedClass(Orders.class)
                        .addAnnotatedClass(MainService.class)
                        .addAnnotatedClass(SubService.class)
                        .addAnnotatedClass(Admin.class)
                        .addAnnotatedClass(Customer.class)
                        .addAnnotatedClass(Specialist.class)
                        .buildMetadata()
                        .buildSessionFactory();
    }

    public static SessionFactory getInstance() {
        return SessionFactoryHelper.INSTANCE;
    }
}
