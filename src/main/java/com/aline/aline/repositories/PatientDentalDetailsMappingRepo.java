package com.aline.aline.repositories;

import com.aline.aline.entities.PatientDentalDetailsMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientDentalDetailsMappingRepo extends MongoRepository<PatientDentalDetailsMapping, String> {
    Optional<PatientDentalDetailsMapping> findByPatientID(String patientID);
}
