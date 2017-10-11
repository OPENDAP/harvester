package org.opendap.harvester;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.opendap.harvester.configuration.SpringMongoDataConfig;
import org.opendap.harvester.configuration.springdatajava8.LocalDateTimeToStringConverter;
import org.opendap.harvester.configuration.springdatajava8.StringToLocalDateTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = {HarvesterApplication.class, SpringMongoDataConfig.class}))
@EnableMongoRepositories
public class HarvesterApplicationTest extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Bean
    public Mongo mongo() throws Exception {
        return new Fongo("mongo-test").getMongo();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    @Override
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new LocalDateTimeToStringConverter());
        converterList.add(new StringToLocalDateTimeConverter());
        return new CustomConversions(converterList);
    }
}

