/**
 * Entry point to spring boot application. It responses for creating all Spring Beans
 * like @Controller, @Service and @Configuration
 * All of this Spring beans types are singletons by default.
 */
package org.opendap.harvester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

// Replaced these with what follows as part of an upgrade to sprint-boot 1.5.7
// This new version of spring-boot works with gradle 4 (so might fix the travis
// build issues). jhrg 10/7/17
//
// import org.springframework.boot.context.embedded.ErrorPage;
// import org.springframework.boot.context.web.SpringBootServletInitializer;

//import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class ReporterApplication extends SpringBootServletInitializer {
	private static final Logger log = LoggerFactory.getLogger(ReporterApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ReporterApplication.class);
	}

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(ReporterApplication.class, args);
		log.info("Application has been started");
	}
}
