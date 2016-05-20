package org.opendap.harvester.service.impl;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendap.harvester.HarvesterApplicationTest;
import org.opendap.harvester.dao.HyraxInstanceRepository;
import org.opendap.harvester.service.HyraxInstanceService;
import org.opendap.harvester.service.LogCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HarvesterApplicationTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LogCollectorServiceTest {
    @Autowired
    private LogCollectorService logCollectorService;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    /**
     * no need to test ir right now
     */
    public void testThatCollectLogsReturnsCorrectDtos(){
    }

}
