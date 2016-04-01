package org.opendap.harvester.entity.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public abstract class BaseEntity {
    @Id
    @Getter @Setter
    private String id;
}
