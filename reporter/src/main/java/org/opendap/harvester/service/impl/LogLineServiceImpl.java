package org.opendap.harvester.service.impl;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.opendap.harvester.entity.LinePatternConfig;
import org.opendap.harvester.entity.LogLine;
import org.opendap.harvester.entity.dto.LogLineDto;
import org.opendap.harvester.service.LogLineService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

@Service
public class LogLineServiceImpl implements LogLineService {
    private static final String TIME_FIELD = "localDateTime";

    @Override
    public LocalDateTime getLocalDateTime(LogLine logLine) {
        Map<String, String> logLineValues = logLine.getValues();
        return toGMT(logLineValues.get(TIME_FIELD));
    }

    @Override
    public LogLine parseLogLine(String line, LinePatternConfig config) {
        if (line == null || config == null) {
            return null;
        }

        String[] names = config.getNames();
        Map<String, String> logLine = new HashMap<>();

        Matcher matcher = config.getPattern().matcher(line.trim());
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                logLine.put(names[i-1], matcher.group(i));
            }
        }

        return LogLine.builder().values(logLine).build();
    }

    @Override
    public LogLineDto buildDto(LogLine logLine) {
        return LogLineDto.builder().values(logLine.getValues()).build();
    }

    /**
     * Return the date/time information in GMT given that it has been recorded in the
     * local time and we know what time zone that is.
     *
     * @param zoneString Time zone for the log data, using the strings recognized by the
     *                   java DateTime class.
     * @return A new LocalDateTime instance.
     */
    private LocalDateTime toGMT(String zoneString){
        DateTime zonedDateTime =
                DateTime.parse(zoneString, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS Z"));
        return zonedDateTime.toDateTime(DateTimeZone.UTC).toLocalDateTime();
    }
}
