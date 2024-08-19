package com.aline.aline.services;

import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientTreatmentPlanService {
    PatientTreatmentPlanDto createTreatmentPlan(PatientTreatmentPlanDto patientTreatmentPlanDto);
    PatientTreatmentPlanDto saveDraftForTreatmentPlan(PatientTreatmentPlanDto patientTreatmentPlanDto);
    void sendTreatmentPlanModificationToDoctor(String patientID, String treatmentPlanID, PatientTreatmentPlanDto patientTreatmentPlanDto) throws BadRequestException;
    PatientTreatmentPlanDto getTreatmentPlan(String patientID, String treatmentPlanID);
    List<PatientTreatmentPlanDto> getAllTreatmentPlanForPatientID(String patientID);
    PatientTreatmentPlanDto getTreatmentPlanDraft(String patientID, String treatmentPlanID);
    List<String> getAllHistoricalVersionIDsForTreatmentPlan(String patientID, String treatmentPlanID);
    PatientTreatmentPlanDto getHistoricalTreatmentPlan(String patientID, String treatmentPlanID, String treatmentPlanVersionID);
}
