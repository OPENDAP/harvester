/**
 * Service implementation. All business logic should be here.
 * Call to db are initiating from this place via Repositories
 */
package org.opendap.harvester.service.impl;

import org.joda.time.LocalDateTime;
import org.opendap.harvester.config.ConfigurationExtractor;
import org.opendap.harvester.entity.LinePattern;
import org.opendap.harvester.entity.LinePatternConfig;
import org.opendap.harvester.entity.LogData;
import org.opendap.harvester.entity.LogLine;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.entity.dto.LogLineDto;
import org.opendap.harvester.service.LogExtractionService;
import org.opendap.harvester.service.LogLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class LogExtractionServiceImpl implements LogExtractionService {
    @Autowired
    private LogLineService logLineService;

    @Autowired
    private ConfigurationExtractor configurationExtractor;

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

    /**
     * @todo This method will fail if the pattern doesn't match. In that case is returns
     * a record/line that is 'values:""' repeated N time  where N is the number of fields
     * in the pattern regex. There's no error message.
     * 
     * @param since
     * @return
     * @throws IOException
     */
    private List<LogLine> getLogLines(LocalDateTime since) throws IOException {
        LinePattern linePattern = configurationExtractor.getLinePattern();
        LinePatternConfig config = LinePatternConfig.builder()
                .pattern(Pattern.compile(linePattern.getRegexp()))
                .names(linePattern.getNames().split(";"))
                .build();

        List<String> allLines = Files.readAllLines(Paths.get(configurationExtractor.getHyraxLogfilePath()), Charset.defaultCharset());
        List<LogLine> parsedLines = new ArrayList<>();
        for (String line : allLines){
            LogLine parsedLogLine = logLineService.parseLogLine(line, config);
            if (since == null || logLineService.getLocalDateTime(parsedLogLine).isAfter(since)){
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
