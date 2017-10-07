package org.opendap.harvester.entity.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@Builder
@Getter @Setter
public class LogLine extends BaseEntity {
    private String hyraxInstanceId;
    private Map<String, String> values;

}
