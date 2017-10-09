package org.opendap.harvester.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Data transfer object for returning results form BE to FE.
 * 
 * @note The Anonymous log lines have nine fields each enclosed in []
 * (line breaks added for sanity's sake):
 * [Mozilla/5.0 ...] [366DFB37E0D4D83BE7C70266B74F267D] [2016-06-23T17:50:27.468 +0100] [   19 ms] 
 * [200] [      12] [GET] [/opendap/hyrax/data/nc/coads_climatology.nc.dods] 
 * [COADSX[0:1:179],COADSY[0:1:89],TIME[0:1:11]] 
 */

@Builder
@Getter @Setter
public class LogLineDto {
    private Map<String, String> values;
}
