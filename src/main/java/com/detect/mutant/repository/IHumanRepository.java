package com.detect.mutant.repository;

import com.detect.mutant.model.HumanData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IHumanRepository extends MongoRepository<HumanData, String> {
    double countByIsMutantTrue();
    double countByIsMutantFalse();
}