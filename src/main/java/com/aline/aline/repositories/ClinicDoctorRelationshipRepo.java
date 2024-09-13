package com.aline.aline.repositories;

import com.aline.aline.entities.ClinicDoctorRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicDoctorRelationshipRepo extends MongoRepository<ClinicDoctorRelationship, String> {
    //@Query("{ $and: [{'doctorID' : '?0'}, {'clinicID' : '?1' }] }")
    Optional<ClinicDoctorRelationship> findByDoctorIDAndClinicID(String doctorID, String clinicID);

    List<ClinicDoctorRelationship> findAllByClinicID(String clinicID);

    List<ClinicDoctorRelationship> findAllByDoctorID(String doctorID);
}
