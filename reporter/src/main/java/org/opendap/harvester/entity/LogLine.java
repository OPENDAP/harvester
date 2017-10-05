package org.opendap.harvester.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Builder
@Getter @Setter
public class LogLine {
    /**
     * <pattern>
     *      [%X{host}] [%X{ident}] [%d{yyyy-MM-dd'T'HH:mm:ss.SSS Z}]
     *      [%8X{duration}] [%X{http_status}] [%8X{ID}] [%X{VERB}] [%X{resourceID}]
     *      [%X{query}] %n
     * </pattern>
     */
<<<<<<< HEAD

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
=======
    @Getter @Setter
    private String host;
    @Getter @Setter
    private String sessionId;
    /*
    @Getter @Setter
    private String userId;
    */
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
>>>>>>> master

}
