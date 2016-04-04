/**
 * Service implementation. All business logic should be here.
 * Call to db are initiating from this place via Repositories
 */
package org.opendap.harvester.service.impl;

import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.service.LogExtractionService;
import org.opendap.harvester.service.LogLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class LogExtractionServiceImpl implements LogExtractionService {
    @Autowired
    private LogLineService logLineService;

    @Value("${hyrax.logfile.path}")
    private String hyraxLogfilePath;

    @Override
    public LogData extractLogDataSince(LocalDateTime time) throws IOException {
        return LogData.builder()
                .lines(Files.lines(Paths.get(hyraxLogfilePath))
                            .map(logLineService::parseLogLine)
                            .filter(logLine -> logLine.getLocalDateTime().isAfter(time))
                            .collect(Collectors.toList()))
                .build();
    }
}
