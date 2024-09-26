package com.aline.aline.customMapper;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;

public class DeepClone {

    public static PatientTreatmentPlan deepClone(PatientTreatmentPlan patientTreatmentPlan){
        PatientTreatmentPlan newPlan = new PatientTreatmentPlan();

        newPlan.setId(patientTreatmentPlan.getId());
        newPlan.setLabel(patientTreatmentPlan.getLabel());
        newPlan.setPatientID(patientTreatmentPlan.getPatientID());
        newPlan.setMalocclusionTag(patientTreatmentPlan.getMalocclusionTag());
        newPlan.setCaseAssessment(patientTreatmentPlan.getCaseAssessment());
        newPlan.setTreatmentPlanSummary(patientTreatmentPlan.getTreatmentPlanSummary());
        newPlan.setUpperSteps(patientTreatmentPlan.getUpperSteps());
        newPlan.setLowerSteps(patientTreatmentPlan.getLowerSteps());
        newPlan.setExpectedDuration(patientTreatmentPlan.getExpectedDuration());
        newPlan.setTreatmentPlanCategory(patientTreatmentPlan.getTreatmentPlanCategory());
        newPlan.setPrice(patientTreatmentPlan.getPrice());
        newPlan.setIprAndAttachmentReports(patientTreatmentPlan.getIprAndAttachmentReports());
        newPlan.setTreatmentSimulationsURL(patientTreatmentPlan.getTreatmentSimulationsURL());
        newPlan.setTreatmentSimulationsAttachments(patientTreatmentPlan.getTreatmentSimulationsAttachments());
        newPlan.setCreatedOn(patientTreatmentPlan.getCreatedOn());
        newPlan.setUpdatedOn(patientTreatmentPlan.getUpdatedOn());

        return newPlan;
    }
}
