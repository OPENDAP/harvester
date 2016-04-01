package org.opendap.harvester.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LogLine {
    @Getter @Setter
    private String logLine;
}
