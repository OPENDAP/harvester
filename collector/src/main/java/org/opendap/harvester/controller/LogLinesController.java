package org.opendap.harvester.controller;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogLineDto;
import org.opendap.harvester.entity.dto.model.HyraxInstanceNameModel;
import org.opendap.harvester.entity.dto.model.RegisterModel;
import org.opendap.harvester.service.HyraxInstanceService;
import org.opendap.harvester.service.LogLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("harvester/logLines")
public class LogLinesController {
    @Autowired
    private LogLineService logLineService;

    @Autowired
    private HyraxInstanceService hyraxInstanceService;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseBody
    public List<LogLineDto> findAllLogLines(@Valid @ModelAttribute HyraxInstanceNameModel hyraxInstanceNameModel){
        HyraxInstance hyraxInstance = hyraxInstanceService.findHyraxInstanceByName(
                hyraxInstanceNameModel.getHyraxInstanceName());
        if (hyraxInstance == null){
            return Collections.emptyList();
        }
        return logLineService.findLogLines(hyraxInstance.getId());
    }

    @RequestMapping(path = "/string", method = RequestMethod.GET)
    @ResponseBody
    public String findAllLogLinesAsString(@Valid @ModelAttribute HyraxInstanceNameModel hyraxInstanceNameModel){
        HyraxInstance hyraxInstance = hyraxInstanceService.findHyraxInstanceByName(
                hyraxInstanceNameModel.getHyraxInstanceName());
        if (hyraxInstance == null){
            return "";
        }
        return logLineService.findLogLinesAsString(hyraxInstance.getId());
    }



}
