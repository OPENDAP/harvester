package org.opendap.harvester.controller;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.opendap.harvester.entity.dto.ApplicationDto;
import org.opendap.harvester.entity.document.Application;
import org.opendap.harvester.entity.dto.model.RegisterModel;
import org.opendap.harvester.service.ApplicationRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ApplicationDto register(@Valid @ModelAttribute RegisterModel registerModel) throws Exception {
        Application register = applicationRegisterService.register(
                registerModel.getServer(),
                registerModel.getPing(),
                registerModel.getLog());
        return applicationRegisterService.buildDto(register);
    }

    @RequestMapping(path = "/allApplications", method = RequestMethod.GET)
    @ResponseBody
    public List<ApplicationDto> allApplications(
            @RequestParam(defaultValue = "true") Boolean onlyActive)
            throws Exception {
        return applicationRegisterService.allApplications(onlyActive)
                .map(applicationRegisterService::buildDto)
                .collect(Collectors.toList());
    }
//
//    @ExceptionHandler
//    private void exception(){
//
//    }

}
