package org.achareh.service;

import org.achareh.base.service.BaseService;
import org.achareh.model.user.Admin;

import java.util.Optional;

public interface AdminService extends BaseService<Admin, Long> {
    Optional<Admin> findByEmailAndPassword(String email, String password);

}
