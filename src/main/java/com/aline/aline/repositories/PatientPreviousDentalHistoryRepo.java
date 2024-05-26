package com.aline.aline.repositories;

import com.aline.aline.entities.PatientPreviousDentalHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientPreviousDentalHistoryRepo extends MongoRepository<PatientPreviousDentalHistory, String> {
    Optional<PatientPreviousDentalHistory> findByPatientID(String patientID);
}
