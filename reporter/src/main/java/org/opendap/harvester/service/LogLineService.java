package org.opendap.harvester.service;

import org.opendap.harvester.entity.LogLine;
import org.opendap.harvester.entity.dto.LogLineDto;

public interface LogLineService {
    LogLine parseLogLine(String line);
    LogLineDto buildDto(LogLine logLine);
}
