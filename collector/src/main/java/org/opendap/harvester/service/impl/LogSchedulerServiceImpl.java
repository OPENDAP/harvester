package org.opendap.harvester.service.impl;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.service.HyraxInstanceService;
import org.opendap.harvester.service.LogCollectorService;
import org.opendap.harvester.service.LogLineService;
import org.opendap.harvester.service.LogSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Service which periodically check do we need to get new data from reporter
 */
@Service
public class LogSchedulerServiceImpl implements LogSchedulerService {
    @Autowired
    private HyraxInstanceService hyraxInstanceService;

    @Autowired
    private LogCollectorService logCollectorService;

    @Autowired
    private LogLineService logLineService;

    @Scheduled(fixedDelay = 1000)
    void checkHyraxInstances() {
        hyraxInstanceService.allHyraxInstances(true)
                .filter(this::isTimeToCheck)
                .forEach(hi -> {
                    LogDataDto logDataDto;
                    ZonedDateTime utc = ZonedDateTime.now(ZoneId.of("UTC"));
                    if (hi.getLastAccessTime() == null){
                        logDataDto = logCollectorService.collectAllLogs(hi);
                    } else {
                        logDataDto = logCollectorService.collectLogs(hi, hi.getLastAccessTime());
                    }
                    hyraxInstanceService.updateLastAccessTime(hi, utc.toLocalDateTime());
                    logLineService.addLogLines(hi.getId(), logDataDto.getLines());
                });
    }

    private boolean isTimeToCheck(HyraxInstance hyraxInstance) {
        if (hyraxInstance.getLastAccessTime() == null) {
            return true;
        }
        return Duration.between(hyraxInstance.getLastAccessTime(), ZonedDateTime.now(ZoneId.of("UTC")))
                .getSeconds() > hyraxInstance.getPing();
    }
}
