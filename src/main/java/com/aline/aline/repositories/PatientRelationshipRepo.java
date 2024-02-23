package com.aline.aline.repositories;

import com.aline.aline.entities.PatientRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRelationshipRepo extends MongoRepository<PatientRelationship, String> {
}
