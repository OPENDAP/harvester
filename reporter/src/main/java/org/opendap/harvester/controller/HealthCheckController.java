package org.opendap.harvester.controller;

import org.opendap.harvester.config.ConfigurationExtractor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ConfigurationExtractor configurationExtractor;

    @RequestMapping(path = "/healthcheck", method = RequestMethod.GET)
    public String healthCheck(){
        return "Application is working! Version = " + reporterVersion;
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test(){
        try {
            return configurationExtractor.getHyraxLogfilePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

}
