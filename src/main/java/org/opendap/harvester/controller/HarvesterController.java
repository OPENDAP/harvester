package org.opendap.harvester.controller;

import org.opendap.harvester.controller.dto.ApplicationDto;
import org.opendap.harvester.entity.Application;
import org.opendap.harvester.service.ApplicationRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Controller
@RequestMapping("/harvester")
public class HarvesterController {
    @Autowired
    private ApplicationRegisterService applicationRegisterService;

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    @ResponseBody
    public ApplicationDto register(@RequestParam String server,@RequestParam int ping,
                                   @RequestParam int log  ) throws URISyntaxException {
        Application register = applicationRegisterService.register(server, ping, log);
        return applicationRegisterService.buildDto(register);
    }

    @ExceptionHandler
    private void exception(){

    }

}
