package org.opendap.harvester;

import org.junit.Test;
import org.junit.runner.RunWith;

//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HarvesterApplicationTest.class})
@WebAppConfiguration
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = {HarvesterApplicationTest.class})
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ServiceExample {

	@Test
	public void contextLoads() {
	}

}


