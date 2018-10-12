package org.opendap.harvester.controller;

import org.opendap.harvester.config.ConfigurationExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @todo I think this code can be merged into ReporterController. jhrg 10/8/17
 */
@RestController
@RequestMapping("/")
public class HealthCheckController {
    @Value("${reporter.version}")
    private String reporterVersion;

    @Autowired
    private ConfigurationExtractor configurationExtractor;

    @RequestMapping(path = "/healthcheck", method = RequestMethod.GET)
    public String healthCheck(){
        return "Reporter Application, Version = " + reporterVersion;
    }

    /**
     * @todo Make this a more complete response. It could return the default and 
     * configured parameter values.
     * 
     * @return A string returned to the browser/client.
     */
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String test(){
        try {
            return configurationExtractor.getHyraxLogfilePath();
        } catch (Exception e) {
        	    e.printStackTrace();
        }
        return "Error: Could not get the Hyrax log file path";
    }

}
