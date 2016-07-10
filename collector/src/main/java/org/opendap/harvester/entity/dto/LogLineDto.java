package org.opendap.harvester.entity.dto;

import lombok.*;

import java.util.Map;

/**
 * Data transfer object for returning results form BE to FE.
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter @Setter
public class LogLineDto {
    private Map<String, String> values;

}
