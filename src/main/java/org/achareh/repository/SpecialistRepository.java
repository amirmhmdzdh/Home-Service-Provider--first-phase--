package org.achareh.repository;

import org.achareh.base.repository.BaseRepository;
import org.achareh.model.user.Specialist;

import java.util.Optional;

public interface SpecialistRepository extends BaseRepository<Specialist,Long> {

    Optional<Specialist> findByEmailAndPassword(String email, String password);

}
