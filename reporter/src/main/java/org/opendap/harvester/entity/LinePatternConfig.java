package org.opendap.harvester.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter @Setter
@Builder
public class LinePatternConfig {
    private String[] names;
    private Pattern pattern;
}
