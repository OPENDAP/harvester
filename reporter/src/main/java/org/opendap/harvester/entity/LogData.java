package org.opendap.harvester.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
public class LogData {
    @Getter @Setter
    private List<LogLine> lines;
}
