package org.opendap.harvester.service;

import org.opendap.harvester.entity.dto.ApplicationDto;
import org.opendap.harvester.entity.document.Application;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

public interface ApplicationRegisterService {
    Application register(String server, int ping, int log) throws Exception;
    Stream<Application> allApplications();
    Stream<Application> allApplications(boolean onlyActive);
    ApplicationDto buildDto(Application application);

}
