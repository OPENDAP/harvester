package org.opendap.harvester.service.impl;

import org.opendap.harvester.dao.HyraxInstanceRepository;
import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.service.LogCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class LogCollectorServiceServiceImpl implements LogCollectorService {
    @Autowired
    private HyraxInstanceRepository hyraxInstanceRepository;
    @Override
    public LogDataDto collectLogs(HyraxInstance hyraxInstance, LocalDateTime since) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                new URI(hyraxInstance.getName() + "/reporter/log?since=" + since),
                LogDataDto.class);
    }
}
