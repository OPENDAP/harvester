package org.opendap.harvester.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class ApplicationDto {
    @Getter
    @Setter
    private String name;
}
