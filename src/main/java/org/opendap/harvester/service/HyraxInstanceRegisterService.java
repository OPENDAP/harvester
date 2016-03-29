package org.opendap.harvester.service;

import org.opendap.harvester.entity.dto.HyraxInstanceDto;
import org.opendap.harvester.entity.document.HyraxInstance;

import java.util.stream.Stream;

public interface HyraxInstanceRegisterService {
    HyraxInstance register(String server, int ping, int log) throws Exception;
    Stream<HyraxInstance> allHyraxInstances();
    Stream<HyraxInstance> allHyraxInstances(boolean onlyActive);
    HyraxInstanceDto buildDto(HyraxInstance hyraxInstance);

}
