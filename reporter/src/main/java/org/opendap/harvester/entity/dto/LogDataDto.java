package org.opendap.harvester.entity.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.opendap.harvester.entity.LogLine;

import java.util.List;

/**
 * Data transfer object for returning results form BE to FE.
 */

@Builder
public class LogDataDto {
    @Getter @Setter
    private List<LogLineDto> lines;
}
