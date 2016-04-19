package org.opendap.harvester.controller;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogLineDto;
import org.opendap.harvester.service.HyraxInstanceService;
import org.opendap.harvester.service.LogLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<LogLineDto> findAllLogLines(@RequestParam String hyraxInstanceName){
        HyraxInstance hyraxInstance = hyraxInstanceService.findHyraxInstanceByName(hyraxInstanceName);
        return logLineService.findLogLines(hyraxInstance.getId());
    }



}
