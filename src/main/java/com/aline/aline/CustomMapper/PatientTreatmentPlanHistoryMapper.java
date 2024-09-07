package com.aline.aline.CustomMapper;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class PatientTreatmentPlanHistoryMapper {

    public PatientTreatmentPlan mapper(PatientTreatmentPlanHistory patientTreatmentPlanHistory) {

        PatientTreatmentPlan patientTreatmentPlan = new PatientTreatmentPlan();

        patientTreatmentPlan.setId(new ObjectId(patientTreatmentPlanHistory.getTreatmentPlanID()));
        patientTreatmentPlan.setPatientID(patientTreatmentPlanHistory.getPatientID());
        patientTreatmentPlan.setMalocclusionTag(patientTreatmentPlanHistory.getMalocclusionTag());
        patientTreatmentPlan.setCaseAssessment(patientTreatmentPlanHistory.getCaseAssessment());
        patientTreatmentPlan.setTreatmentPlanSummary(patientTreatmentPlanHistory.getTreatmentPlanSummary());
        patientTreatmentPlan.setUpperSteps(patientTreatmentPlanHistory.getUpperSteps());
        patientTreatmentPlan.setLowerSteps(patientTreatmentPlanHistory.getLowerSteps());
        patientTreatmentPlan.setExpectedDuration(patientTreatmentPlanHistory.getExpectedDuration());
        patientTreatmentPlan.setTreatmentPlanCategory(patientTreatmentPlanHistory.getTreatmentPlanCategory());
        patientTreatmentPlan.setPrice(patientTreatmentPlanHistory.getPrice());
        patientTreatmentPlan.setIprAndAttachmentReports(patientTreatmentPlanHistory.getIprAndAttachmentReports());
        patientTreatmentPlan.setTreatmentSimulationsURL(patientTreatmentPlanHistory.getTreatmentSimulationsURL());
        patientTreatmentPlan.setTreatmentSimulationsAttachments(patientTreatmentPlanHistory.getTreatmentSimulationsAttachments());
        patientTreatmentPlan.setCreatedOn(patientTreatmentPlanHistory.getCreatedOn());

        return patientTreatmentPlan;
    }

    public PatientTreatmentPlanHistory mapper(PatientTreatmentPlan patientTreatmentPlan) {

        PatientTreatmentPlanHistory patientTreatmentPlanHistory = new PatientTreatmentPlanHistory();

        if(patientTreatmentPlan.getId() != null) patientTreatmentPlanHistory.setTreatmentPlanID(patientTreatmentPlan.getId().toString());
        patientTreatmentPlanHistory.setPatientID(patientTreatmentPlan.getPatientID());
        patientTreatmentPlanHistory.setMalocclusionTag(patientTreatmentPlan.getMalocclusionTag());
        patientTreatmentPlanHistory.setCaseAssessment(patientTreatmentPlan.getCaseAssessment());
        patientTreatmentPlanHistory.setTreatmentPlanSummary(patientTreatmentPlan.getTreatmentPlanSummary());
        patientTreatmentPlanHistory.setUpperSteps(patientTreatmentPlan.getUpperSteps());
        patientTreatmentPlanHistory.setLowerSteps(patientTreatmentPlan.getLowerSteps());
        patientTreatmentPlanHistory.setExpectedDuration(patientTreatmentPlan.getExpectedDuration());
        patientTreatmentPlanHistory.setTreatmentPlanCategory(patientTreatmentPlan.getTreatmentPlanCategory());
        patientTreatmentPlanHistory.setPrice(patientTreatmentPlan.getPrice());
        patientTreatmentPlanHistory.setIprAndAttachmentReports(patientTreatmentPlan.getIprAndAttachmentReports());
        patientTreatmentPlanHistory.setTreatmentSimulationsURL(patientTreatmentPlan.getTreatmentSimulationsURL());
        patientTreatmentPlanHistory.setTreatmentSimulationsAttachments(patientTreatmentPlan.getTreatmentSimulationsAttachments());
        patientTreatmentPlanHistory.setOriginalCreatedOn(patientTreatmentPlan.getCreatedOn());

        return patientTreatmentPlanHistory;
    }

    public PatientTreatmentPlanDto DtoMapper(PatientTreatmentPlanHistory patientTreatmentPlanHistory) {

        PatientTreatmentPlanDto patientTreatmentPlan = new PatientTreatmentPlanDto();

        patientTreatmentPlan.setId(patientTreatmentPlanHistory.getId().toString());
        patientTreatmentPlan.setPatientID(patientTreatmentPlanHistory.getPatientID());
        patientTreatmentPlan.setTreatmentPlanID(patientTreatmentPlanHistory.getTreatmentPlanID());
        patientTreatmentPlan.setMalocclusionTag(patientTreatmentPlanHistory.getMalocclusionTag());
        patientTreatmentPlan.setCaseAssessment(patientTreatmentPlanHistory.getCaseAssessment());
        patientTreatmentPlan.setTreatmentPlanSummary(patientTreatmentPlanHistory.getTreatmentPlanSummary());
        patientTreatmentPlan.setUpperSteps(patientTreatmentPlanHistory.getUpperSteps());
        patientTreatmentPlan.setLowerSteps(patientTreatmentPlanHistory.getLowerSteps());
        patientTreatmentPlan.setExpectedDuration(patientTreatmentPlanHistory.getExpectedDuration());
        patientTreatmentPlan.setTreatmentPlanCategory(patientTreatmentPlanHistory.getTreatmentPlanCategory());
        patientTreatmentPlan.setPrice(patientTreatmentPlanHistory.getPrice());
        patientTreatmentPlan.setIprAndAttachmentReports(patientTreatmentPlanHistory.getIprAndAttachmentReports());
        patientTreatmentPlan.setTreatmentSimulationsURL(patientTreatmentPlanHistory.getTreatmentSimulationsURL());
        patientTreatmentPlan.setTreatmentSimulationsAttachments(patientTreatmentPlanHistory.getTreatmentSimulationsAttachments());

        return patientTreatmentPlan;
    }

}
