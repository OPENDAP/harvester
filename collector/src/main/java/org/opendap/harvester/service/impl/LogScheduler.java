package org.opendap.harvester.service.impl;

import org.opendap.harvester.dao.HyraxInstanceRepository;
import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogDataDto;
import org.opendap.harvester.service.LogCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Component which will be associated with each active HyraxInstance.
 * It will create cron behaviour and will call LogCollector for getting logs.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogScheduler {
    @Autowired
    private HyraxInstanceRepository hyraxInstanceRepository;

    @Autowired
    private LogCollectorService logCollectorService;

    private HyraxInstance hyraxInstance;
    private ScheduledExecutorService scheduler;

    public void setUp(String hyraxInstanceId){
        if (hyraxInstance != null){
            throw new IllegalStateException("Instance is already constructed");
        }
        hyraxInstance = hyraxInstanceRepository.findByIdAndActiveTrue(hyraxInstanceId);
        scheduler = Executors.newScheduledThreadPool(1);
        Integer ping = hyraxInstance.getPing();
        scheduler.scheduleAtFixedRate(getTask(), ping, ping, TimeUnit.SECONDS);
    }

    private Runnable getTask() {
        return () -> {
            System.out.println("--------------");
            System.out.println(Instant.now().toString());
            System.out.println("Getting Logs from " + hyraxInstance.getName());
            LogDataDto logDataDto = logCollectorService.collectAllLogs(hyraxInstance);
            int size = logDataDto.getLines().size();
            System.out.println("Received: " + size + " lines");
            System.out.println("--------------");
        };
    }
}
