/**
 * Entry point for REST call. All controllers receives REST request outside and reroute
 * them to internal application services. After that it returns results.
 */
package org.opendap.harvester.controller;

import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.service.LogExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * This annotations tell us about what type of Spring bean it is.
 * It is Contrller. It means that it can receives REST requests.
 */
@Controller
@RequestMapping("/reporter")
public class ReporterController {
    /**
     * Autowired automatically inject some of the HyraxInstanceRegisterService implementations to this
     * class field. It will happen on class instantiating stage.
     * After that it can be used in this class like service endpoint.
     */
    @Autowired
    private LogExtractionService logExtractionService;


    @RequestMapping(path = "/log", method = RequestMethod.GET)
    @ResponseBody
    public LogDataDto getLogsSince(@RequestParam String since) throws Exception {
        // Calling service method and returning result
        LocalDateTime localDateTime = LocalDateTime.parse(since);
        LogData logData = logExtractionService.extractLogDataSince(localDateTime);
        return logExtractionService.buildDto(logData);
    }

}
