package org.opendap.harvester.service.impl;

import org.opendap.harvester.controller.dto.ApplicationDto;
import org.opendap.harvester.dao.ApplicationRepository;
import org.opendap.harvester.entity.Application;
import org.opendap.harvester.service.ApplicationRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class ApplicationRegisterServiceImpl implements ApplicationRegisterService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Application register(String server, int ping, int log) throws URISyntaxException {
        checkDomainName(server);
        Application application = new Application();
        application.setName(server);
        application.setPing(ping);
        application.setLog(log);
        Application saved = applicationRepository.save(application);
        return saved;
    }

    private void checkDomainName(String server) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(
            restTemplate.getForObject(new URI(server + "/version"), String.class));
    }

    @Override
    public ApplicationDto buildDto(Application application) {
        return ApplicationDto.builder().name(application.getName()).build();
    }
}
