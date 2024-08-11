package com.aline.aline.repositories.PatientTreatmentPlan;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientTreatmentPlanDraftRepo extends MongoRepository<PatientTreatmentPlanDraft, String> {
    Optional<PatientTreatmentPlanDraft> findByPatientIDAndTreatmentPlanID(String patientID, String treatmentPlanID);
    Optional<PatientTreatmentPlanDraft> findByPatientIDAndTreatmentPlanIDAndDoctorID(String patientID, String treatmentPlanID, String doctorID);
    Optional<PatientTreatmentPlanDraft> findByPatientIDAndTreatmentPlanIDAndClinicID(String patientID, String treatmentPlanID, String clinicID);
}
