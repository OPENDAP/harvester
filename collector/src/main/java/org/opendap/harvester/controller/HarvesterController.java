/**
 * Entry point for REST call. All controllers receives REST request outside and reroute
 * them to internal application services. After that it returns results.
 */
package org.opendap.harvester.controller;

import org.opendap.harvester.entity.dto.HyraxInstanceDto;
import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.model.RegisterModel;
import org.opendap.harvester.service.HyraxInstanceRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This annotations tell us about what type of Spring bean it is.
 * It is Contrller. It means that it can receives REST requests.
 */
@Controller
@RequestMapping("/harvester")
public class HarvesterController {
    /**
     * Autowired automatically inject some of the HyraxInstanceRegisterService implementations to this
     * class field. It will happen on class instantiating stage.
     * After that it can be used in this class like service endpoint.
     */
    @Autowired
    private HyraxInstanceRegisterService hyraxInstanceRegisterService;

    /**
     * Called when /harvester/registration request come. Automatically setting up requst attributes to special object.
     * @param registerModel
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    @ResponseBody
    public HyraxInstanceDto register(@Valid @ModelAttribute RegisterModel registerModel) throws Exception {
        // Calling service method and returning result
        HyraxInstance register = hyraxInstanceRegisterService.register(
                registerModel.getServerUrl(),
                StringUtils.isEmpty(registerModel.getReporterUrl()) ?
                        registerModel.getServerUrl() : registerModel.getReporterUrl(),
                registerModel.getPing(),
                registerModel.getLog());
        return hyraxInstanceRegisterService.buildDto(register);
    }

    @RequestMapping(path = "/allHyraxInstances", method = RequestMethod.GET)
    @ResponseBody
    public List<HyraxInstanceDto> allHyraxInstances(
            @RequestParam(defaultValue = "true") Boolean onlyActive)
            throws Exception {
        return hyraxInstanceRegisterService.allHyraxInstances(onlyActive)
                .map(hyraxInstanceRegisterService::buildDto)
                .collect(Collectors.toList());
    }
//
//    @ExceptionHandler
//    private void exception(){
//
//    }

}
