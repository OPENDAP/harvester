package org.opendap.harvester.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
public class LogLineDto {
    @Getter @Setter
    private String host;
    @Getter @Setter
    private String sessionId;
    /*
    @Getter @Setter
    private String userId;
    */
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
    /// The resourceID is the same as the DAP dataset - typically a file
    @Getter @Setter
    private String resourceId;
    /// The query is the DAP constraint expression
    @Getter @Setter
    private String query;

}
