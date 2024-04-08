package com.aline.aline.repositories;

import com.aline.aline.entities.ClinicDoctorRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicDoctorRelationshipRepo extends MongoRepository<ClinicDoctorRelationship, String> {
    Optional<ClinicDoctorRelationship> findByDoctorIDAndClinicID(String doctorID, String clinicID);
}
