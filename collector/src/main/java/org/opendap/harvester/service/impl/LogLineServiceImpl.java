package org.opendap.harvester.service.impl;

import org.opendap.harvester.dao.HyraxInstanceRepository;
import org.opendap.harvester.dao.LogLineRepository;
import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.document.LogLine;
import org.opendap.harvester.entity.dto.LogLineDto;
import org.opendap.harvester.service.HyraxInstanceService;
import org.opendap.harvester.service.LogLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogLineServiceImpl implements LogLineService {
    @Autowired
    private HyraxInstanceRepository hyraxInstanceRepository;

    @Autowired
    private LogLineRepository logLineRepository;

    @Override
    public void addLogLines(String hyraxInstanceId, List<LogLineDto> logLineDtoList) {
        HyraxInstance hyraxInstance = hyraxInstanceRepository.findByIdAndActiveTrue(hyraxInstanceId);
        if (hyraxInstance != null) {
            List<LogLine> logLines = logLineDtoList.stream()
                    .map(dto -> LogLine.builder()
                            .hyraxInstanceId(hyraxInstanceId)
                            .host(dto.getHost())
                            .sessionId(dto.getSessionId())
                            .userId(dto.getUserId())
                            .localDateTime(dto.getLocalDateTime())
                            .duration(dto.getDuration())
                            .httpStatus(dto.getHttpStatus())
                            .requestId(dto.getRequestId())
                            .httpVerb(dto.getHttpVerb())
                            .resourceId(dto.getResourceId())
                            .query(dto.getQuery())
                            .build())
                    .collect(Collectors.toList());
            logLineRepository.save(logLines);
        }
    }

    @Override
    public List<LogLineDto> findLogLines(String hyraxInstanceId) {
        return logLineRepository.streamByHyraxInstanceId(hyraxInstanceId)
                .map(this::buildDto)
                .collect(Collectors.toList());
    }

    @Override
    public LogLineDto buildDto(LogLine logLine) {
        return LogLineDto.builder()
                .host(logLine.getHost())
                .sessionId(logLine.getSessionId())
                .userId(logLine.getUserId())
                .localDateTime(logLine.getLocalDateTime())
                .duration(logLine.getDuration())
                .httpStatus(logLine.getHttpStatus())
                .requestId(logLine.getRequestId())
                .httpVerb(logLine.getHttpVerb())
                .resourceId(logLine.getResourceId())
                .query(logLine.getQuery())
                .build();
    }
}
