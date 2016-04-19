/**
 * Model class which describes how to bind attributes from REST request to java object.
 */
package org.opendap.harvester.entity.dto.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Required;

public class RegisterModel {
    @Getter @Setter
    private String serverUrl;
    @Getter @Setter
    private String reporterUrl;
    @Getter @Setter
    private int ping;
    @Getter @Setter
    private int log;
}
