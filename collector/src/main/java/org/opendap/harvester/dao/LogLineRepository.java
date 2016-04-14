package org.opendap.harvester.dao;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.opendap.harvester.entity.document.LogLine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface LogLineRepository extends MongoRepository<LogLine, String> {
    Stream<LogLine> streamByHyraxInstanceId(String hyraxInstanceId);
}
