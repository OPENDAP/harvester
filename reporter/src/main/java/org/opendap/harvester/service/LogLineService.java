package org.opendap.harvester.service;

import org.opendap.harvester.entity.LogLine;

public interface LogLineService {
    LogLine parseLogLine(String line);
}
