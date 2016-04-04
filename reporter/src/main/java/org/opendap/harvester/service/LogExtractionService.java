package org.opendap.harvester.service;


import org.opendap.harvester.entity.LogData;

import java.io.IOException;
import java.time.LocalDateTime;

public interface LogExtractionService {
    LogData extractLogDataSince(LocalDateTime time) throws IOException;
}
