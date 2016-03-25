package org.opendap.harvester.entity.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Document
@Builder
public class Application extends BaseEntity {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int ping = 0;
    @Getter @Setter
    private int log = 0;
    @Getter @Setter
    private String versionNumber;
    @Getter @Setter
    private LocalDateTime registrationTime;
    @Getter @Setter
    private LocalDateTime lastAccessTime;




}
