package org.opendap.harvester.service;

import org.opendap.harvester.controller.dto.ApplicationDto;
import org.opendap.harvester.entity.Application;

import java.net.URISyntaxException;

public interface ApplicationRegisterService {
    Application register(String server, int ping, int log) throws URISyntaxException;
    ApplicationDto buildDto(Application application);

}
