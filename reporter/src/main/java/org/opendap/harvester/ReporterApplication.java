/**
 * Entry point to spring boot application. It responses for creating all Spring Beans
 * like @Controller, @Service and @Configuration
 * All of this Spring beans types are singletons by default.
 */
package org.opendap.harvester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReporterApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReporterApplication.class, args);
	}
}
