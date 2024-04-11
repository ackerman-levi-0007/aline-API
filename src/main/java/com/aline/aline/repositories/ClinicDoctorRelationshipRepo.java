package com.aline.aline.repositories;

import com.aline.aline.entities.ClinicDoctorRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicDoctorRelationshipRepo extends MongoRepository<ClinicDoctorRelationship, String> {
    //@Query("{ $and: [{'doctorID' : '?0'}, {'clinicID' : '?1' }] }")
    Optional<ClinicDoctorRelationship> findByDoctorIDAndClinicID(String doctorID, String clinicID);
}
