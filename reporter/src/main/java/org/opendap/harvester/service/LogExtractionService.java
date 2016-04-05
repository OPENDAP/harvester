package org.opendap.harvester.service;


import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.entity.dto.LogDataDto;

import java.io.IOException;
import java.time.LocalDateTime;

public interface LogExtractionService {
    LogData extractLogDataSince(LocalDateTime time) throws IOException;
    LogDataDto buildDto(LogData logData);
}
