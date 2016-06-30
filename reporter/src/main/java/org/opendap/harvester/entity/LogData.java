package org.opendap.harvester.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class LogData {
    private List<LogLine> lines;
}
