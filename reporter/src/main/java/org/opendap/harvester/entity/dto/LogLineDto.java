package org.opendap.harvester.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object for returning results form BE to FE.
 */

@Builder
@Getter @Setter
public class LogLineDto {
    private String host;
    private String sessionId;
    private String userId;
    private String localDateTime;
    private String duration;
    private String httpStatus;
    private Long requestId;
    private String httpVerb;
    private String resourceId;
    private String query;
}
