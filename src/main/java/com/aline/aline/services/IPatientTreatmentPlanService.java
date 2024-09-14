package com.aline.aline.services;

import com.aline.aline.enums.TreatmentPlanType;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientTreatmentPlanService {
    PatientTreatmentPlanDto getTreatmentPlan(String patientID, int rebootID, String treatmentPlanID, TreatmentPlanType treatmentPlanType) throws BadRequestException;
    List<PatientTreatmentPlanDto> getAllTreatmentPlanForPatientID(String patientID);
    void createDraft(String patientID, int rebootID, PatientTreatmentPlanDto patientTreatmentPlanDto);
    void updateDraft(String patientID, int rebootID, PatientTreatmentPlanDto patientTreatmentPlanDto);
    void sendPlanModification(String patientID, int rebootID, String draftID);
    void approvePlan(String patientID, int rebootID, String planID);
    void planRequestModification(String patientID, int rebootID, String planID);
}
