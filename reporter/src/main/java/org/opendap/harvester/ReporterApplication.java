/**
 * Entry point to spring boot application. It responses for creating all Spring Beans
 * like @Controller, @Service and @Configuration
 * All of this Spring beans types are singletons by default.
 */
package org.opendap.harvester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReporterApplication extends SpringBootServletInitializer {

//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//		return new EmbeddedServletContainerCustomizer() {
//			@Override
//			public void customize(ConfigurableEmbeddedServletContainer container) {
//
//				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//
//				container.addErrorPages(error401Page, error404Page, error500Page);
//			}
//		};
//	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ReporterApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ReporterApplication.class, args);
	}
}
