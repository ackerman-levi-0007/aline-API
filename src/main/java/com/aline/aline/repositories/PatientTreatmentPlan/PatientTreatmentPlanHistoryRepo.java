package com.aline.aline.repositories.PatientTreatmentPlan;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientTreatmentPlanHistoryRepo extends MongoRepository<PatientTreatmentPlanHistory, String> {
    Optional<PatientTreatmentPlanHistory> findByIdAndPatientIDAndTreatmentPlanID(String treatmentPlanVersionID, String patientID, String treatmentPlanID);
}
