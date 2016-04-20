/**
 * Service implementation. All business logic should be here.
 * Call to db are initiating from this place via Repositories
 */
package org.opendap.harvester.service.impl;

import org.joda.time.LocalDateTime;
import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.entity.LogLine;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.entity.dto.LogLineDto;
import org.opendap.harvester.service.LogExtractionService;
import org.opendap.harvester.service.LogLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogExtractionServiceImpl implements LogExtractionService {
    @Autowired
    private LogLineService logLineService;

    @Value("${hyrax.logfile.path}")
    private String hyraxLogfilePath;

    @Override
    public LogData extractLogDataSince(LocalDateTime time) throws IOException {
        return LogData.builder()
                .lines(getLogLines(time))
                .build();
    }

    @Override
    public LogData extractAllLogData() throws IOException {
        return LogData.builder()
                .lines(getLogLines())
                .build();
    }
    private List<LogLine> getLogLines() throws IOException {
        return getLogLines(null);
    }

    private List<LogLine> getLogLines(LocalDateTime since) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(hyraxLogfilePath), Charset.defaultCharset());
        List<LogLine> parsedLines = new ArrayList<>();
        for (String line : allLines){
            LogLine parsedLogLine = logLineService.parseLogLine(line);
            if (since == null || parsedLogLine.getLocalDateTime().isAfter(since)){
                parsedLines.add(parsedLogLine);
            }
        }
        return parsedLines;
    }

    @Override
    public LogDataDto buildDto(LogData logData) {
        List<LogLineDto> logLineDtos = new ArrayList<>();
        for (LogLine logLine : logData.getLines()) {
            logLineDtos.add(logLineService.buildDto(logLine));
        }
        return LogDataDto.builder()
                .lines(logLineDtos)
                .build();
    }
}
