package org.opendap.harvester;

import org.opendap.harvester.config.ConfigurationExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.EnvironmentTestUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = {ReporterApplication.class}))
@TestPropertySource(properties = "hyrax.default.ping=3600")
public class ReporterApplicationTest {

    @Bean
    public MockServletContext mockServletContext(){
        return new MockServletContext();
    }

}

