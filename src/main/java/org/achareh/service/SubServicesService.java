package org.achareh.service;

import org.achareh.base.service.BaseService;
import org.achareh.model.service.SubService;

import java.util.List;

public interface SubServicesService extends BaseService<SubService, Long> {

    List<SubService> getSubServicesByMainServiceId(Long mainServiceId);

}
