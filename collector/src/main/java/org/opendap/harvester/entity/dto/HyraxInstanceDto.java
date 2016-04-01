/**
 * Data transfer object for returning results form BE to FE.
 */
package org.opendap.harvester.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
public class HyraxInstanceDto {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int ping = 0;
    @Getter @Setter
    private int log = 0;
    @Getter @Setter
    private String versionNumber;
    @Getter @Setter
    private String registrationTime;
    @Getter @Setter
    private String lastAccessTime;
    @Getter @Setter
    private Boolean active = false;
}
