package org.opendap.harvester.configuration.springdatajava8;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }
}