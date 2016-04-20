package org.opendap.harvester.service;


import org.joda.time.LocalDateTime;
import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.entity.dto.LogDataDto;

import java.io.IOException;

public interface LogExtractionService {
    LogData extractLogDataSince(LocalDateTime time) throws IOException;
    LogData extractAllLogData() throws IOException;

    LogDataDto buildDto(LogData logData);
}
