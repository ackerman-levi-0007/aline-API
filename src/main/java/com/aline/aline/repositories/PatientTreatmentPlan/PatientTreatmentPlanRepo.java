package com.aline.aline.repositories.PatientTreatmentPlan;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientTreatmentPlanRepo extends MongoRepository<PatientTreatmentPlan, String> {
    Optional<PatientTreatmentPlan> findByIdAndPatientID(String treatmentPlanID, String patientID);
    Optional<List<PatientTreatmentPlan>> findByPatientID(String patientID);
}
