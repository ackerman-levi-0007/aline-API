package com.aline.aline.CustomMapper;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import org.springframework.stereotype.Service;

@Service
public class PatientTreatmentPlanMapper {

    public PatientTreatmentPlanDto DtoMapper(PatientTreatmentPlan patientTreatmentPlan) {

        PatientTreatmentPlanDto patientTreatmentPlanDto = new PatientTreatmentPlanDto();

        patientTreatmentPlanDto.setId(patientTreatmentPlan.getId().toString());
        patientTreatmentPlanDto.setPatientID(patientTreatmentPlan.getPatientID());
        patientTreatmentPlanDto.setTreatmentPlanID(patientTreatmentPlan.getId().toString());
        patientTreatmentPlanDto.setMalocclusionTag(patientTreatmentPlan.getMalocclusionTag());
        patientTreatmentPlanDto.setCaseAssessment(patientTreatmentPlan.getCaseAssessment());
        patientTreatmentPlanDto.setTreatmentPlanSummary(patientTreatmentPlan.getTreatmentPlanSummary());
        patientTreatmentPlanDto.setUpperSteps(patientTreatmentPlan.getUpperSteps());
        patientTreatmentPlanDto.setLowerSteps(patientTreatmentPlan.getLowerSteps());
        patientTreatmentPlanDto.setExpectedDuration(patientTreatmentPlan.getExpectedDuration());
        patientTreatmentPlanDto.setTreatmentPlanCategory(patientTreatmentPlan.getTreatmentPlanCategory());
        patientTreatmentPlanDto.setPrice(patientTreatmentPlan.getPrice());
        patientTreatmentPlanDto.setIprAndAttachmentReports(patientTreatmentPlan.getIprAndAttachmentReports());
        patientTreatmentPlanDto.setTreatmentSimulationsURL(patientTreatmentPlan.getTreatmentSimulationsURL());
        patientTreatmentPlanDto.setTreatmentSimulationsAttachments(patientTreatmentPlan.getTreatmentSimulationsAttachments());

        return patientTreatmentPlanDto;
    }
}
