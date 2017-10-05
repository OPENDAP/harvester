package org.opendap.harvester.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Builder
@Getter @Setter
public class LogLine {
//    private String host;
//    private String sessionId;
//    private String userId;
//    private LocalDateTime localDateTime;
//    private String duration;
//    private String httpStatus;
//    private Long requestId;
//    private String httpVerb;
//    private String resourceId;
//    private String query;
    private Map<String, String> values;
}
