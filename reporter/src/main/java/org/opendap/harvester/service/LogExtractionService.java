package org.opendap.harvester.service;


import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.entity.dto.LogDataDto;

import java.time.LocalDateTime;

public interface LogExtractionService {
    LogData extractLogDataSince(LocalDateTime time);
    LogDataDto buildDto(LogData logData);
}
