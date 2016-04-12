package org.opendap.harvester.service;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogDataDto;

import java.time.LocalDateTime;

public interface LogCollectorService {
    LogDataDto collectLogs(HyraxInstance hyraxInstance, LocalDateTime since);
    LogDataDto collectAllLogs(HyraxInstance hyraxInstance);
}
