package org.achareh.repository;

import org.achareh.base.repository.BaseRepository;
import org.achareh.model.user.Admin;

import java.util.Optional;

public interface AdminRepository extends BaseRepository<Admin, Long> {

    Optional<Admin> findByEmailAndPassword(String email, String password);

}
