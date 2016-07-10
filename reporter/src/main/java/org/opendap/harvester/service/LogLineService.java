package org.opendap.harvester.service;

import org.joda.time.LocalDateTime;
import org.opendap.harvester.entity.LinePatternConfig;
import org.opendap.harvester.entity.LogLine;
import org.opendap.harvester.entity.dto.LogLineDto;

public interface LogLineService {
    LocalDateTime getLocalDateTime(LogLine logLine);
    LogLine parseLogLine(String line, LinePatternConfig config);
    LogLineDto buildDto(LogLine logLine);
}
