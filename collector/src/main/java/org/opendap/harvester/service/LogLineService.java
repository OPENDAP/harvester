package org.opendap.harvester.service;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.document.LogLine;
import org.opendap.harvester.entity.dto.LogLineDto;

import java.util.List;

public interface LogLineService {
    void addLogLines(String hyraxInstanceId, List<LogLineDto> logLineDtoList);
    List<LogLineDto> findLogLines(String hyraxInstanceId);
    String findLogLinesAsString(String hyraxInstanceId);
    LogLineDto buildDto(LogLine logLine);
}
