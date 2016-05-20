package org.opendap.harvester.service.impl;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendap.harvester.HarvesterApplicationTest;
import org.opendap.harvester.dao.HyraxInstanceRepository;
import org.opendap.harvester.dao.LogLineRepository;
import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.dto.LogLineDto;
import org.opendap.harvester.service.LogCollectorService;
import org.opendap.harvester.service.LogLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HarvesterApplicationTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LogLineServiceTest {
    @Autowired
    private LogLineService logLineService;
    @Autowired
    private LogLineRepository logLineRepository;
    @Autowired
    private HyraxInstanceRepository hyraxInstanceRepository;

    @Test
    public void testThatCanNotAddLogLinesWithNullHyraxInstanceId(){
        logLineService.addLogLines(null, Collections.singletonList(new LogLineDto()));
        assertEquals(0, logLineRepository.findAll().size());
    }

    @Test
    public void testThatCanNotAddLogLinesToInactiveHyraxInstance(){
        String id = buildHyraxInstance(false).getId();
        logLineService.addLogLines(id, Collections.singletonList(new LogLineDto()));
        assertEquals(0, logLineRepository.findAll().size());
    }

    @Test
    public void testThatSuccessfullyAddLogLinesToHyraxInstance(){
        String id = buildHyraxInstance(true).getId();
        logLineService.addLogLines(id, Stream.generate(LogLineDto::new).limit(4).collect(Collectors.toList()));
        assertEquals(4, logLineRepository.findAll().size());
    }

    private HyraxInstance buildHyraxInstance(boolean active) {
        HyraxInstance hyraxInstance = HyraxInstance.builder()
                .name(String.valueOf(new Random().nextInt()))
                .active(active)
                .build();
        return hyraxInstanceRepository.save(hyraxInstance);
    }

}
