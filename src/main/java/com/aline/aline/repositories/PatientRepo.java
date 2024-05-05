package com.aline.aline.repositories;

import com.aline.aline.entities.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepo extends MongoRepository<Patient, String> {
    Optional<Patient> findByIdAndDoctorID(String patientID, String doctorID);

    Optional<Patient> findByIdAndClinicID(String patientID, String clinicID);
}
