/**
 * Service implementation. All business logic should be here.
 * Call to db are initiating from this place via Repositories
 */
package org.opendap.harvester.service.impl;

import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.entity.LogLine;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.service.LogExtractionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class LogExtractionServiceImpl implements LogExtractionService {
    @Override
    public LogData extractLogDataSince(LocalDateTime time) {
        return LogData.builder()
                .lines(Collections.singletonList(LogLine.builder()
                        .logLine("Test Line")
                        .build()))
                .build();
    }

    @Override
    public LogDataDto buildDto(LogData logData) {
        return LogDataDto.builder()
                .lines(
                        logData.getLines().stream()
                                .map(LogLine::getLogLine)
                                .collect(Collectors.toList()))
                .build();
    }
}
