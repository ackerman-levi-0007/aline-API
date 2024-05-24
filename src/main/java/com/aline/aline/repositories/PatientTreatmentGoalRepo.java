package com.aline.aline.repositories;

import com.aline.aline.entities.PatientTreatmentGoal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientTreatmentGoalRepo extends MongoRepository<PatientTreatmentGoal, String> {
    Optional<PatientTreatmentGoal> findByPatientID(String patientID);
}
