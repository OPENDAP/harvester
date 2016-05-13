package org.opendap.harvester.controller;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.service.LogCollectorService;
import org.opendap.harvester.service.LogSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {
    @Value("${harvester.version}")
    private String harvesterVersion;

    @Autowired
    private LogSchedulerService logSchedulerService;

    @RequestMapping(path = "/healthcheck", method = RequestMethod.GET)
    public String healthCheck(){
        return "Application is working! Version = " + harvesterVersion;
    }

}
