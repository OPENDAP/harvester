package org.opendap.harvester.service.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendap.harvester.ReporterApplicationTest;
import org.opendap.harvester.service.LogLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ReporterApplicationTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LogLineServiceTest {
    private static final String CORRECT = "[0:0:0:0:0:0:0:1] [-] [-] [2016-03-29T11:43:43.422 -0700] [   13 ms] [200] [       8] [HTTP-GET] [/opendap/hyrax/data/nc/fnoc1.nc.dods] []";
    private static final String INCORRECT = "[0:0:0:0:0:0:0:1] [-] [2016-03-29T11:43:43.422 -0700] [   13 ms] [200] [       8] [HTTP-GET] [/opendap/hyrax/data/nc/fnoc1.nc.dods] []";
    @Autowired
    private LogLineService logLineService;


    @Test
    public void testThatCanNotParseNullLogLine(){
        assertNull(logLineService.parseLogLine(null, null));
    }

    @Test
    public void testThatCanNotParseEmptyLogLine(){
        assertNull(logLineService.parseLogLine(null, null));
    }

    @Test
    public void testThatCanNotParseIncorrectStructuredLogLine(){
        assertNull(logLineService.parseLogLine(INCORRECT, null));
    }

}
