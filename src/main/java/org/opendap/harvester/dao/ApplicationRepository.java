package org.opendap.harvester.dao;

import org.opendap.harvester.entity.document.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
    Stream<Application> streamByName(String name);
    Stream<Application> streamByActiveTrue();
}
