package org.opendap.harvester.controller;

import org.opendap.harvester.entity.dto.ApplicationDto;
import org.opendap.harvester.entity.document.Application;
import org.opendap.harvester.service.ApplicationRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/harvester")
public class HarvesterController {
    @Autowired
    private ApplicationRegisterService applicationRegisterService;

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    @ResponseBody
    public ApplicationDto register(@RequestParam String server,@RequestParam int ping,
                                   @RequestParam int log  ) throws Exception {
        Application register = applicationRegisterService.register(server, ping, log);
        return applicationRegisterService.buildDto(register);
    }

    @RequestMapping(path = "/allApplications", method = RequestMethod.GET)
    @ResponseBody
    public List<ApplicationDto> allApplications() throws Exception {
        return applicationRegisterService.allApplications()
                .stream()
                .map(applicationRegisterService::buildDto)
                .collect(Collectors.toList());
    }
//
//    @ExceptionHandler
//    private void exception(){
//
//    }

}
