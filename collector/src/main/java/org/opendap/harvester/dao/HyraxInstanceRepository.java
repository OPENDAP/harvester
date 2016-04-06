package org.opendap.harvester.dao;

import org.opendap.harvester.entity.document.HyraxInstance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface HyraxInstanceRepository extends MongoRepository<HyraxInstance, String> {
    Stream<HyraxInstance> streamByName(String name);
    Stream<HyraxInstance> streamByActiveTrue();
    HyraxInstance findByIdAndActiveTrue(String id);
}
