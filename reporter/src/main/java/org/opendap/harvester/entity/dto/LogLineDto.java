package org.opendap.harvester.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Data transfer object for returning results form BE to FE.
 * 
 * @note The Anonymous log lines look like (line breaks added for sanity's sake):
 * [Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.41 Safari/537.36] 
 * [366DFB37E0D4D83BE7C70266B74F267D] [2016-06-23T17:50:27.468 +0100] [   19 ms] 
 * [200] [      12] [GET] [/opendap/hyrax/data/nc/coads_climatology.nc.dods] 
 * [COADSX[0:1:179],COADSY[0:1:89],TIME[0:1:11]] 
 */

@Builder
@Getter @Setter
public class LogLineDto {
//    private String host;
//    private String sessionId;
//    private String userId;
//    private String localDateTime;
//    private String duration;
//    private String httpStatus;
//    private Long requestId;
//    private String httpVerb;
//    private String resourceId;
//    private String query;
    private Map<String, String> values;
}
