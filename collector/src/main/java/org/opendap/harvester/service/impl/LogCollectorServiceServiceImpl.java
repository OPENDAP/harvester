package org.opendap.harvester.service.impl;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.service.LogCollectorService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;

/**
 * Log collector
 * Service which will get data from reporter
 */

@Service
public class LogCollectorServiceServiceImpl implements LogCollectorService {
    @Override
    public LogDataDto collectLogs(HyraxInstance hyraxInstance, LocalDateTime since) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                new URI(hyraxInstance.getName() + "/reporter/log?since=" + since),
                LogDataDto.class);
    }
}
