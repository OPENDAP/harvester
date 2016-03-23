package org.opendap.harvester.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Application extends BaseEntity {
    @Getter @Setter
    private String name;
}
