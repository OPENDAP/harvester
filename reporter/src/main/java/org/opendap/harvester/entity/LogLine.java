package org.opendap.harvester.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Builder
@Getter @Setter
public class LogLine {
    private Map<String, String> values;
}
