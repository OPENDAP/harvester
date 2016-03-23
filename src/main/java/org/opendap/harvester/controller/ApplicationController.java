package org.opendap.harvester.controller;

import org.opendap.harvester.controller.dto.ApplicationDto;
import org.opendap.harvester.entity.Application;
import org.opendap.harvester.service.ApplicationRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationRegisterService applicationRegisterService;

    @RequestMapping(path = "/register", method = RequestMethod.PUT)
    @ResponseBody
    public ApplicationDto register(@RequestParam String name){
        Application register = applicationRegisterService.register(name);
        return applicationRegisterService.buildDto(register);
    }

}
