package com.aline.aline.services;

import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.enums.TreatmentPlanType;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientTreatmentPlanService {
    PatientTreatmentPlanDto createTreatmentPlan(PatientTreatmentPlanDto patientTreatmentPlanDto);
    PatientTreatmentPlanDto saveDraftForTreatmentPlan(PatientTreatmentPlanDto patientTreatmentPlanDto);
    void sendTreatmentPlanModificationToDoctor(String patientID, String treatmentPlanID, PatientTreatmentPlanDto patientTreatmentPlanDto) throws BadRequestException;
    PatientTreatmentPlanDto getTreatmentPlan(String patientID, String treatmentPlanID, TreatmentPlanType treatmentPlanType) throws BadRequestException;
    List<PatientTreatmentPlanDto> getAllTreatmentPlanForPatientID(String patientID);
}
