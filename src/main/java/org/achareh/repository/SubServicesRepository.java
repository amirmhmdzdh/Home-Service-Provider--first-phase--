package org.achareh.repository;

import org.achareh.base.repository.BaseRepository;
import org.achareh.model.service.SubService;

import java.util.List;

public interface SubServicesRepository extends BaseRepository<SubService, Long> {


    List<SubService> getSubServicesByMainServiceId(Long mainServiceId);
}
