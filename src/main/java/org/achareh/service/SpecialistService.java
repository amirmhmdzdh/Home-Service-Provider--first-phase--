package org.achareh.service;

import org.achareh.base.service.BaseService;
import org.achareh.model.user.Specialist;

import java.util.Optional;

public interface SpecialistService extends BaseService<Specialist,Long> {

    Optional<Specialist> findByEmailAndPassword(String email, String password);

}
