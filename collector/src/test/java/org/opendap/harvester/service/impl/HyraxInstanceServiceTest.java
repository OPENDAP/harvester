package org.opendap.harvester.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.opendap.harvester.HarvesterApplicationTest;
import org.opendap.harvester.dao.HyraxInstanceRepository;
import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.service.HyraxInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HarvesterApplicationTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HyraxInstanceServiceTest {
    @Autowired
    private HyraxInstanceService hyraxInstanceService;

    @Autowired
    private HyraxInstanceRepository hyraxInstanceRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Test(expected = IllegalArgumentException.class)
    public void testThatRegisterFailsOnEmptyUrl() throws Exception {
        hyraxInstanceService.register(null, "http://localhost:8080", 1L ,2);
    }

    @Test
    public void testThatRegisterCompleteSuccessful() throws Exception {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("http://localhost:8080/version"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "<HyraxCombinedVersion>\n" +
                        "<Hyrax version=\"1.12.2\"/>\n" +
                        "<OLFS version=\"1.14.1\"/>\n" +
                        "</HyraxCombinedVersion>", MediaType.APPLICATION_XML));
        mockServer.expect(requestTo("http://localhost:8081/healthcheck"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("",MediaType.TEXT_PLAIN));
        mockServer.expect(requestTo("http://localhost:8081/defaultPing"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("5" ,MediaType.APPLICATION_JSON));

        hyraxInstanceService.register("http://localhost:8080", "http://localhost:8081", 1L ,2);
        mockServer.verify();
    }

    @Test
    public void testThatAllHyraxInstancesReturnsCorrectValueOnEmptyBase() throws Exception {
        assertEquals(0, hyraxInstanceService.allHyraxInstances().count());
    }

    @Test
    public void testThatAllHyraxInstancesReturnsCorrectCountOfInstances() throws Exception {
        hyraxInstanceRepository.save(buildHyraxInstance(true));
        hyraxInstanceRepository.save(buildHyraxInstance(true));
        hyraxInstanceRepository.save(buildHyraxInstance(false));
        hyraxInstanceRepository.save(buildHyraxInstance(true));
        assertEquals(4, hyraxInstanceService.allHyraxInstances().count());
    }

    @Test
    public void testThatAllHyraxInstancesReturnsCorrectCountOfActiveInstances() throws Exception {
        hyraxInstanceRepository.save(buildHyraxInstance(true));
        hyraxInstanceRepository.save(buildHyraxInstance(true));
        hyraxInstanceRepository.save(buildHyraxInstance(false));
        hyraxInstanceRepository.save(buildHyraxInstance(true));
        assertEquals(3, hyraxInstanceService.allHyraxInstances(true).count());
    }


    private HyraxInstance buildHyraxInstance(boolean active) {
        return HyraxInstance.builder()
                .name(String.valueOf(new Random().nextInt()))
                .active(active)
                .build();
    }


}
