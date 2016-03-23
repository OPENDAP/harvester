package org.opendap.harvester.service;

import org.opendap.harvester.controller.dto.ApplicationDto;
import org.opendap.harvester.entity.Application;

public interface ApplicationRegisterService {
    Application register(String name);
    ApplicationDto buildDto(Application application);

}
