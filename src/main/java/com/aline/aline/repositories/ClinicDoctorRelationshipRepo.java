package com.aline.aline.repositories;

import com.aline.aline.entities.ClinicDoctorRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicDoctorRelationshipRepo extends MongoRepository<ClinicDoctorRelationship, String> {
}
