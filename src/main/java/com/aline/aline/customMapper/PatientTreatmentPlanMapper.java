package com.aline.aline.customMapper;

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
        patientTreatmentPlanDto.setLabel(patientTreatmentPlan.getLabel());
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

        patientTreatmentPlan.setPatientID(patientTreatmentPlanDraft.getPatientID());
        patientTreatmentPlan.setLabel(patientTreatmentPlanDraft.getLabel());
        patientTreatmentPlan.setMalocclusionTag(patientTreatmentPlanDraft.getMalocclusionTag());
        patientTreatmentPlan.setCaseAssessment(patientTreatmentPlanDraft.getCaseAssessment());
        patientTreatmentPlan.setTreatmentPlanSummary(patientTreatmentPlanDraft.getTreatmentPlanSummary());
        patientTreatmentPlan.setUpperSteps(patientTreatmentPlanDraft.getUpperSteps());
        patientTreatmentPlan.setLowerSteps(patientTreatmentPlanDraft.getLowerSteps());
        patientTreatmentPlan.setExpectedDuration(patientTreatmentPlanDraft.getExpectedDuration());
        patientTreatmentPlan.setTreatmentPlanCategory(patientTreatmentPlanDraft.getTreatmentPlanCategory());
        patientTreatmentPlan.setPrice(patientTreatmentPlanDraft.getPrice());
        patientTreatmentPlan.setIprAndAttachmentReports(patientTreatmentPlanDraft.getIprAndAttachmentReports());
        patientTreatmentPlan.setTreatmentSimulationsURL(patientTreatmentPlanDraft.getTreatmentSimulationsURL());
        patientTreatmentPlan.setTreatmentSimulationsAttachments(patientTreatmentPlanDraft.getTreatmentSimulationsAttachments());

        return patientTreatmentPlan;
    }

    public PatientTreatmentPlan PlanDraftSetter(PatientTreatmentPlan patientTreatmentPlan, PatientTreatmentPlanDraft patientTreatmentPlanDraft) {

        patientTreatmentPlan.setMalocclusionTag(patientTreatmentPlanDraft.getMalocclusionTag());
        patientTreatmentPlan.setCaseAssessment(patientTreatmentPlanDraft.getCaseAssessment());
        patientTreatmentPlan.setTreatmentPlanSummary(patientTreatmentPlanDraft.getTreatmentPlanSummary());
        patientTreatmentPlan.setUpperSteps(patientTreatmentPlanDraft.getUpperSteps());
        patientTreatmentPlan.setLowerSteps(patientTreatmentPlanDraft.getLowerSteps());
        patientTreatmentPlan.setExpectedDuration(patientTreatmentPlanDraft.getExpectedDuration());
        patientTreatmentPlan.setTreatmentPlanCategory(patientTreatmentPlanDraft.getTreatmentPlanCategory());
        patientTreatmentPlan.setPrice(patientTreatmentPlanDraft.getPrice());
        patientTreatmentPlan.setIprAndAttachmentReports(patientTreatmentPlanDraft.getIprAndAttachmentReports());
        patientTreatmentPlan.setTreatmentSimulationsURL(patientTreatmentPlanDraft.getTreatmentSimulationsURL());
        patientTreatmentPlan.setTreatmentSimulationsAttachments(patientTreatmentPlanDraft.getTreatmentSimulationsAttachments());

        return patientTreatmentPlan;
    }
}
