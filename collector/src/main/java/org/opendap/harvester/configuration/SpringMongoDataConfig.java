/**
 * DB Connection configuration
 */
package org.opendap.harvester.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.opendap.harvester.confiuration.springdatajava8.LocalDateTimeToStringConverter;
import org.opendap.harvester.confiuration.springdatajava8.StringToLocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringMongoDataConfig extends AbstractMongoConfiguration {
    @Value("${mongo.db.name}")
    private String databaseName;

    @Value("${mongo.db.server}")
    private String databaseServer;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(databaseServer);
    }

    /**
     * Adding custom LocalDate and LocalTime converters for Mongo DAO.
     * @return
     */
    @Bean
    @Override
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new LocalDateTimeToStringConverter());
        converterList.add(new StringToLocalDateTimeConverter());
        return new CustomConversions(converterList);
    }
}