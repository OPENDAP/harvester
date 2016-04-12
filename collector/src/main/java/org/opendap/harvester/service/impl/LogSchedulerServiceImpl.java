package org.opendap.harvester.service.impl;

import org.opendap.harvester.service.LogSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service will be working with building LogSchedulers instances for each HyraxInstance.
 * Will be invoked by registration service.
 */
@Service
public class LogSchedulerServiceImpl implements LogSchedulerService {
    @Autowired
    private ApplicationContext applicationContext;

    private List<LogScheduler> schedulerList = new ArrayList<>();

    @Override
    public void buildSchedulerAndRun(String hyraxInstanceId) {
        LogScheduler logScheduler = applicationContext.getBean(LogScheduler.class);
        logScheduler.setUp(hyraxInstanceId);
        schedulerList.add(logScheduler);
    }
}
