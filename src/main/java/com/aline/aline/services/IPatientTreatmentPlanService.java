package com.aline.aline.services;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientTreatmentPlanService {
    PatientTreatmentPlan createTreatmentPlan(PatientTreatmentPlan patientTreatmentPlan);
    PatientTreatmentPlan saveDraftForTreatmentPlan(PatientTreatmentPlan patientTreatmentPlan);
    void sendTreatmentPlanModificationToDoctor(String patientID, String treatmentPlanID, PatientTreatmentPlan patientTreatmentPlan) throws BadRequestException;
    PatientTreatmentPlan getTreatmentPlan(String patientID, String treatmentPlanID);
    List<PatientTreatmentPlan> getAllTreatmentPlanForPatientID(String patientID);
    PatientTreatmentPlan getTreatmentPlanDraft(String patientID, String treatmentPlanID);
    List<String> getAllHistoricalVersionIDsForTreatmentPlan(String patientID, String treatmentPlanID);
    PatientTreatmentPlan getHistoricalTreatmentPlan(String patientID, String treatmentPlanID, String treatmentPlanVersionID);
}
