package org.achareh.repository;

import org.achareh.base.repository.BaseRepository;
import org.achareh.model.user.Customer;

import java.util.Optional;

public interface CustomerRepository extends BaseRepository<Customer, Long> {

    Optional<Customer> findByEmailAndPassword(String email, String password);
}
