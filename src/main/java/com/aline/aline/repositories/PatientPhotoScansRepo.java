package com.aline.aline.repositories;

import com.aline.aline.entities.PatientPhotoScans;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientPhotoScansRepo extends MongoRepository<PatientPhotoScans, String> {
    Optional<PatientPhotoScans> findByPatientID(String patientID);
}
