package org.opendap.harvester.controller;

import org.opendap.harvester.service.LogCollectorService;
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
    private LogCollectorService logCollectorService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String healthCheck(){
        return "Application is working! Version = " + harvesterVersion;
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test() {
        return "";
    }


}
