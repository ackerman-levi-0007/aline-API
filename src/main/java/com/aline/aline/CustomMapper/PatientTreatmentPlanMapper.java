package com.aline.aline.CustomMapper;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
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

    public PatientTreatmentPlan DraftMapper(PatientTreatmentPlanDraft patientTreatmentPlanDraft) {

        PatientTreatmentPlan patientTreatmentPlan = new PatientTreatmentPlan();

        patientTreatmentPlan.setPatientID(patientTreatmentPlan.getPatientID());
        patientTreatmentPlan.setMalocclusionTag(patientTreatmentPlan.getMalocclusionTag());
        patientTreatmentPlan.setCaseAssessment(patientTreatmentPlan.getCaseAssessment());
        patientTreatmentPlan.setTreatmentPlanSummary(patientTreatmentPlan.getTreatmentPlanSummary());
        patientTreatmentPlan.setUpperSteps(patientTreatmentPlan.getUpperSteps());
        patientTreatmentPlan.setLowerSteps(patientTreatmentPlan.getLowerSteps());
        patientTreatmentPlan.setExpectedDuration(patientTreatmentPlan.getExpectedDuration());
        patientTreatmentPlan.setTreatmentPlanCategory(patientTreatmentPlan.getTreatmentPlanCategory());
        patientTreatmentPlan.setPrice(patientTreatmentPlan.getPrice());
        patientTreatmentPlan.setIprAndAttachmentReports(patientTreatmentPlan.getIprAndAttachmentReports());
        patientTreatmentPlan.setTreatmentSimulationsURL(patientTreatmentPlan.getTreatmentSimulationsURL());
        patientTreatmentPlan.setTreatmentSimulationsAttachments(patientTreatmentPlan.getTreatmentSimulationsAttachments());

        return patientTreatmentPlan;
    }

    public PatientTreatmentPlan PlanDraftSetter(PatientTreatmentPlan patientTreatmentPlan, PatientTreatmentPlanDraft patientTreatmentPlanDraft) {

        patientTreatmentPlan.setMalocclusionTag(patientTreatmentPlan.getMalocclusionTag());
        patientTreatmentPlan.setCaseAssessment(patientTreatmentPlan.getCaseAssessment());
        patientTreatmentPlan.setTreatmentPlanSummary(patientTreatmentPlan.getTreatmentPlanSummary());
        patientTreatmentPlan.setUpperSteps(patientTreatmentPlan.getUpperSteps());
        patientTreatmentPlan.setLowerSteps(patientTreatmentPlan.getLowerSteps());
        patientTreatmentPlan.setExpectedDuration(patientTreatmentPlan.getExpectedDuration());
        patientTreatmentPlan.setTreatmentPlanCategory(patientTreatmentPlan.getTreatmentPlanCategory());
        patientTreatmentPlan.setPrice(patientTreatmentPlan.getPrice());
        patientTreatmentPlan.setIprAndAttachmentReports(patientTreatmentPlan.getIprAndAttachmentReports());
        patientTreatmentPlan.setTreatmentSimulationsURL(patientTreatmentPlan.getTreatmentSimulationsURL());
        patientTreatmentPlan.setTreatmentSimulationsAttachments(patientTreatmentPlan.getTreatmentSimulationsAttachments());

        return patientTreatmentPlan;
    }
}
