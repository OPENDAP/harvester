/**
 * Data transfer object for returning results form BE to FE.
 */
package org.opendap.harvester.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.opendap.harvester.entity.LogLine;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class LogDataDto {
    @Getter @Setter
    private List<String> lines;
}
