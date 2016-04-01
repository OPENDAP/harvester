package org.opendap.harvester.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthCheckController {
    @Value("${reporter.version}")
    private String reporterVersion;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String healthCheck(){
        return "Application is working! Version = " + reporterVersion;
    }

}
