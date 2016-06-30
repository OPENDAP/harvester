package org.opendap.harvester.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;


@Builder
@Getter @Setter
public class LogLine {
    /**
     * <pattern>
     *      [%X{host}] [%X{ident}] [%X{userid}] [%d{yyyy-MM-dd'T'HH:mm:ss.SSS Z}]
     *      [%8X{duration}] [%X{http_status}] [%8X{ID}] [%X{SOURCE}] [%X{resourceID}]
     *      [%X{query}] %n
     * </pattern>
     */

    private String host;
    private String sessionId;
    private String userId;
    private LocalDateTime localDateTime;
    private String duration;
    private String httpStatus;
    private Long requestId;
    private String httpVerb;
    private String resourceId;
    private String query;

}
