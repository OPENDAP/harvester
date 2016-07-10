package org.opendap.harvester.entity.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data transfer object for returning results form BE to FE.
 */
@Getter @Setter
public class LogDataDto {
    private List<LogLineDto> lines;
}
