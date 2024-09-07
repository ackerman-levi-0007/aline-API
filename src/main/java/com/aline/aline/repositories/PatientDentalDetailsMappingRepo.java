package com.aline.aline.repositories;

import com.aline.aline.entities.PatientDentalDetailsMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientDentalDetailsMappingRepo extends MongoRepository<PatientDentalDetailsMapping, String> {
    Optional<List<PatientDentalDetailsMapping>> findByPatientID(String patientID);
    Optional<PatientDentalDetailsMapping> findByPatientIDAndRebootID(String patientID, int rebootID);
}
