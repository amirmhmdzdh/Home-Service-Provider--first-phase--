package org.achareh.utility;

import org.achareh.connection.SessionFactorySingleton;
import org.achareh.repository.*;
import org.achareh.repository.impl.*;
import org.achareh.service.*;
import org.achareh.service.impl.*;
import org.hibernate.SessionFactory;


public class ApplicationContext {

    private static final SessionFactory SESSION_FACTORY;


    //==================================================================================================================

    private static final AdminRepository ADMIN_REPOSITORY;
    private static final AdminService ADMIN_SERVICE;

    //==================================================================================================================

    private static final CommentRepository COMMENT_REPOSITORY;
    private static final CommentService COMMENT_SERVICE;

    //==================================================================================================================

    private static final CustomerRepository CUSTOMER_REPOSITORY;
    private static final CustomerService CUSTOMER_SERVICE;

    //==================================================================================================================

    private static final MainServiceRepository MAIN_SERVICE_REPOSITORY;
    private static final MainServiceService MAIN_SERVICE_SERVICE;

    //==================================================================================================================

    private static final OfferRepository OFFER_REPOSITORY;
    private static final OfferService OFFER_SERVICE;

    //==================================================================================================================

    private static final OrderRepository ORDER_REPOSITORY;
    private static final OrderService ORDER_SERVICE;

    //==================================================================================================================

    private static final SpecialistRepository SPECIALIST_REPOSITORY;
    private static final SpecialistService SPECIALIST_SERVICE;

    //==================================================================================================================

    private static final SubServicesRepository SUB_SERVICES_REPOSITORY;
    private static final SubServicesService SUB_SERVICES_SERVICE;

    //==================================================================================================================


    static {

        SESSION_FACTORY = SessionFactorySingleton.getInstance();

        //--------------------------------------------------------------------------------------------------------------

        ADMIN_REPOSITORY = new AdminRepositoryImpl(SESSION_FACTORY);
        ADMIN_SERVICE = new AdminServiceImpl(ADMIN_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        COMMENT_REPOSITORY = new CommentRepositoryImpl(SESSION_FACTORY);
        COMMENT_SERVICE = new CommentServiceImpl(COMMENT_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        CUSTOMER_REPOSITORY = new CustomerRepositoryImpl(SESSION_FACTORY);
        CUSTOMER_SERVICE = new CustomerServiceImpl(CUSTOMER_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        MAIN_SERVICE_REPOSITORY = new MainServiceRepositoryImpl(SESSION_FACTORY);
        MAIN_SERVICE_SERVICE = new MainServiceServiceImpl(MAIN_SERVICE_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        OFFER_REPOSITORY = new OfferRepositoryImpl(SESSION_FACTORY);
        OFFER_SERVICE = new OfferServiceImpl(OFFER_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        ORDER_REPOSITORY = new OrderRepositoryImpl(SESSION_FACTORY);
        ORDER_SERVICE = new OrderServiceImpl(ORDER_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        SPECIALIST_REPOSITORY = new SpecialistRepositoryImpl(SESSION_FACTORY);
        SPECIALIST_SERVICE = new SpecialistServiceImpl(SPECIALIST_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

        SUB_SERVICES_REPOSITORY = new SubServicesRepositoryImpl(SESSION_FACTORY);
        SUB_SERVICES_SERVICE = new SubServicesServiceImpl(SUB_SERVICES_REPOSITORY, SESSION_FACTORY);

        //--------------------------------------------------------------------------------------------------------------

    }

    public static AdminService getAdminService() {
        return ADMIN_SERVICE;
    }

    public static CommentService getCommentService() {
        return COMMENT_SERVICE;
    }

    public static CustomerService getCustomerService() {
        return CUSTOMER_SERVICE;
    }

    public static MainServiceService getMainServiceService() {
        return MAIN_SERVICE_SERVICE;
    }

    public static OfferService getOfferService() {
        return OFFER_SERVICE;
    }

    public static OrderService getOrderService() {
        return ORDER_SERVICE;
    }

    public static SpecialistService getSpecialistService() {
        return SPECIALIST_SERVICE;
    }

    public static SubServicesService getSubServicesService() {
        return SUB_SERVICES_SERVICE;
    }
}



