package org.opendap.harvester.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;


@Builder
public class LogLine {
    /**
     * <pattern>
     *      [%X{host}] [%X{ident}] [%X{userid}] [%d{yyyy-MM-dd'T'HH:mm:ss.SSS Z}]
     *      [%8X{duration}] [%X{http_status}] [%8X{ID}] [%X{SOURCE}] [%X{resourceID}]
     *      [%X{query}] %n
     * </pattern>
     */
    @Getter @Setter
    private String host;
    @Getter @Setter
    private String sessionId;
    @Getter @Setter
    private String userId;
    @Getter @Setter
    private LocalDateTime localDateTime;
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
