package org.opendap.harvester.service.impl;

import org.opendap.harvester.controller.dto.ApplicationDto;
import org.opendap.harvester.dao.ApplicationRepository;
import org.opendap.harvester.entity.Application;
import org.opendap.harvester.service.ApplicationRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationRegisterServiceImpl implements ApplicationRegisterService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Application register(String name) {
        Application application = new Application();
        application.setName(name);
        Application saved = applicationRepository.save(application);
        return saved;
    }

    @Override
    public ApplicationDto buildDto(Application application) {
        return ApplicationDto.builder().name(application.getName()).build();
    }
}
