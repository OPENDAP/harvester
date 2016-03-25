package org.opendap.harvester.service;

import org.opendap.harvester.entity.dto.ApplicationDto;
import org.opendap.harvester.entity.document.Application;

import java.net.URISyntaxException;
import java.util.List;

public interface ApplicationRegisterService {
    Application register(String server, int ping, int log) throws Exception;
    List<Application> allApplications();
    ApplicationDto buildDto(Application application);

}
