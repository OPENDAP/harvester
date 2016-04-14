package org.opendap.harvester.entity.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
public class LogLine extends BaseEntity {
    @Getter @Setter
    private String hyraxInstanceId;
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
