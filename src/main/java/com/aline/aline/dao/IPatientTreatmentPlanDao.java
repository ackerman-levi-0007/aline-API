package com.aline.aline.dao;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IPatientTreatmentPlanDao {
    PatientTreatmentPlan createTreatmentPlan(PatientTreatmentPlan patientTreatmentPlan);
    PatientTreatmentPlanDraft saveDraftForTreatmentPlan(PatientTreatmentPlan patientTreatmentPlan);
    PatientTreatmentPlan getTreatmentPlan(String patientID, String treatmentPlanID);
    List<PatientTreatmentPlan> getAllTreatmentPlanForPatientID(String patientID);
    PatientTreatmentPlan getTreatmentPlanDraft(String patientID, String id);
    List<PatientTreatmentPlanHistory> getAllHistoricalVersionIDsForTreatmentPlan(String patientID, String treatmentPlanID);
    PatientTreatmentPlan getHistoricalTreatmentPlan(String patientID, String id);
    void updateTreatmentPlan(String patientID, String treatmentPlanID, PatientTreatmentPlan currentTreatmentPlan, PatientTreatmentPlan updatedTreatmentPlan) throws BadRequestException;
    void moveTreatmentPlanToHistory(PatientTreatmentPlan patientTreatmentPlan);
}
