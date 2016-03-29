/**
 * Model class which describes how to bind attributes from REST request to java object.
 */
package org.opendap.harvester.entity.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

public class RegisterModel {
    @Getter @Setter
    @URL
    private String server;
    @Getter @Setter
    private int ping;
    @Getter @Setter
    private int log;
}
