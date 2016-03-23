package org.opendap.harvester.confiuration.springdatajava8;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String localDateTime) {
        return LocalDateTime.parse(localDateTime);
    }
}
