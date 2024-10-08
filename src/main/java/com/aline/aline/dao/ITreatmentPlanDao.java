package com.aline.aline.dao;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;

import java.util.List;

public interface ITreatmentPlanDao {
    PatientTreatmentPlanDraft saveDraft(PatientTreatmentPlan patientTreatmentPlan);
    PatientTreatmentPlan getTreatmentPlan(String patientID, String treatmentPlanID);
    List<PatientTreatmentPlan> getAllTreatmentPlanForPatientID(String patientID);
    PatientTreatmentPlanDto getTreatmentPlanDraft(String patientID, String id);
    List<PatientTreatmentPlanHistory> getAllHistoricalVersionIDsForTreatmentPlan(String patientID, String treatmentPlanID);
    PatientTreatmentPlanDto getHistoricalTreatmentPlan(String patientID, String id);
    void updateDraft(String patientID, int rebootID, PatientTreatmentPlanDto patientTreatmentPlanDto);
    void savePlan(String patientID, int rebootID, String draftID);
}
