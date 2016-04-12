package org.opendap.harvester.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object for returning results form BE to FE.
 */

public class LogLineDto {
    @Getter @Setter
    private String host;
    @Getter @Setter
    private String sessionId;
    @Getter @Setter
    private String userId;
    @Getter @Setter
    private String localDateTime;
    @Getter @Setter
    private String duration;
    @Getter @Setter
    private String httpStatus;
    @Getter @Setter
    private Long requestId;
    @Getter @Setter
    private String httpVerb;
    @Getter @Setter
    private String resourceId;
    @Getter @Setter
    private String query;

}
