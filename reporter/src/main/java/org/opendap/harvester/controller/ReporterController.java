/**
 * Entry point for REST call. All controllers receives REST request outside and reroute
 * them to internal application services. After that it returns results.
 */
package org.opendap.harvester.controller;

import org.joda.time.LocalDateTime;
import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.service.LogExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


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
    public LogDataDto getLogsSince(@RequestParam(required = false) String since) throws Exception {
        // Calling service method and returning result
        LogData logData;
        if (!StringUtils.isEmpty(since)){
            LocalDateTime localDateTime = LocalDateTime.parse(since);
            logData = logExtractionService.extractLogDataSince(localDateTime);
        } else {
            logData = logExtractionService.extractAllLogData();
        }
        if (logData == null){
            throw new IllegalStateException();
        }
        return logExtractionService.buildDto(logData);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(IllegalStateException e) {
    }

}
