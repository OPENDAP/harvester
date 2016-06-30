package org.opendap.harvester.entity.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data transfer object for returning results form BE to FE.
 */

@Builder
@Getter @Setter
public class LogDataDto {
    private List<LogLineDto> lines;
}
