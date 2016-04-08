package org.opendap.harvester.service.impl;

import org.opendap.harvester.entity.LogLine;
import org.opendap.harvester.entity.dto.LogLineDto;
import org.opendap.harvester.service.LogLineService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogLineServiceImpl implements LogLineService {
    @Override
    public LogLine parseLogLine(String line) {
        if (line == null) {
            return null;
        }

        String[] splitLogLine = line.split("\\] \\[");
        if (splitLogLine.length != 10) {
            return null;
        }
        return LogLine.builder()
                .host(splitLogLine[0].substring(splitLogLine[0].indexOf('[')+1))
                .sessionId(splitLogLine[1])
                .userId(splitLogLine[2])
                .localDateTime(toGMT(splitLogLine[3]))
                .duration(splitLogLine[4])
                .httpStatus(splitLogLine[5])
                .requestId(Long.valueOf(splitLogLine[6].replaceAll(" ", "")))
                .httpVerb(splitLogLine[7])
                .resourceId(splitLogLine[8])
                .query(splitLogLine[9].substring(0,splitLogLine[9].lastIndexOf(']')))
                .build();
    }

    @Override
    public LogLineDto buildDto(LogLine logLine) {
        return LogLineDto.builder()
                .host(logLine.getHost())
                .sessionId(logLine.getSessionId())
                .userId(logLine.getUserId())
                .localDateTime(logLine.getLocalDateTime().toString())
                .duration(logLine.getDuration())
                .httpStatus(logLine.getHttpStatus())
                .requestId(logLine.getRequestId())
                .httpVerb(logLine.getHttpVerb())
                .resourceId(logLine.getResourceId())
                .query(logLine.getQuery())
                .build();
    }

    private LocalDateTime toGMT(String zoneString){
        ZonedDateTime zonedDateTime =
                ZonedDateTime.parse(zoneString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS Z"));
        return zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }
}