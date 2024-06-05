package org.achareh.service;

import org.achareh.base.service.BaseService;
import org.achareh.model.user.Customer;

import java.util.Optional;

public interface CustomerService extends BaseService<Customer,Long> {

    Optional<Customer> findByEmailAndPassword(String email, String password);

}
