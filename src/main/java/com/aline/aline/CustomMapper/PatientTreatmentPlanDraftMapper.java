package com.aline.aline.CustomMapper;

import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class PatientTreatmentPlanDraftMapper {

    public PatientTreatmentPlanDraft mapper(PatientTreatmentPlan patientTreatmentPlan) {

        PatientTreatmentPlanDraft patientTreatmentPlanDraft = new PatientTreatmentPlanDraft();

        patientTreatmentPlanDraft.setTreatmentPlanID(patientTreatmentPlan.getId().toString());
        patientTreatmentPlanDraft.setDoctorID(patientTreatmentPlan.getDoctorID());
        patientTreatmentPlanDraft.setClinicID(patientTreatmentPlan.getClinicID());
        patientTreatmentPlanDraft.setPatientID(patientTreatmentPlan.getPatientID());
        patientTreatmentPlanDraft.setMalocclusionTag(patientTreatmentPlan.getMalocclusionTag());
        patientTreatmentPlanDraft.setCaseAssessment(patientTreatmentPlan.getCaseAssessment());
        patientTreatmentPlanDraft.setTreatmentPlanSummary(patientTreatmentPlan.getTreatmentPlanSummary());
        patientTreatmentPlanDraft.setUpperSteps(patientTreatmentPlan.getUpperSteps());
        patientTreatmentPlanDraft.setLowerSteps(patientTreatmentPlan.getLowerSteps());
        patientTreatmentPlanDraft.setExpectedDuration(patientTreatmentPlan.getExpectedDuration());
        patientTreatmentPlanDraft.setTreatmentPlanCategory(patientTreatmentPlan.getTreatmentPlanCategory());
        patientTreatmentPlanDraft.setPrice(patientTreatmentPlan.getPrice());
        patientTreatmentPlanDraft.setIprAndAttachmentReports(patientTreatmentPlan.getIprAndAttachmentReports());
        patientTreatmentPlanDraft.setTreatmentSimulationsURL(patientTreatmentPlan.getTreatmentSimulationsURL());
        patientTreatmentPlanDraft.setTreatmentSimulationsAttachments(patientTreatmentPlan.getTreatmentSimulationsAttachments());



        return patientTreatmentPlanDraft;
    }

    public PatientTreatmentPlanDraft mapper(PatientTreatmentPlanDraft patientTreatmentPlanDraft, PatientTreatmentPlan patientTreatmentPlan) {

//        patientTreatmentPlanDraft.setTreatmentPlanID(patientTreatmentPlan.getId().toString());
//        patientTreatmentPlanDraft.setDoctorID(patientTreatmentPlan.getDoctorID());
//        patientTreatmentPlanDraft.setClinicID(patientTreatmentPlan.getClinicID());
//        patientTreatmentPlanDraft.setPatientID(patientTreatmentPlan.getPatientID());
        patientTreatmentPlanDraft.setMalocclusionTag(patientTreatmentPlan.getMalocclusionTag());
        patientTreatmentPlanDraft.setCaseAssessment(patientTreatmentPlan.getCaseAssessment());
        patientTreatmentPlanDraft.setTreatmentPlanSummary(patientTreatmentPlan.getTreatmentPlanSummary());
        patientTreatmentPlanDraft.setUpperSteps(patientTreatmentPlan.getUpperSteps());
        patientTreatmentPlanDraft.setLowerSteps(patientTreatmentPlan.getLowerSteps());
        patientTreatmentPlanDraft.setExpectedDuration(patientTreatmentPlan.getExpectedDuration());
        patientTreatmentPlanDraft.setTreatmentPlanCategory(patientTreatmentPlan.getTreatmentPlanCategory());
        patientTreatmentPlanDraft.setPrice(patientTreatmentPlan.getPrice());
        patientTreatmentPlanDraft.setIprAndAttachmentReports(patientTreatmentPlan.getIprAndAttachmentReports());
        patientTreatmentPlanDraft.setTreatmentSimulationsURL(patientTreatmentPlan.getTreatmentSimulationsURL());
        patientTreatmentPlanDraft.setTreatmentSimulationsAttachments(patientTreatmentPlan.getTreatmentSimulationsAttachments());



        return patientTreatmentPlanDraft;
    }

    public PatientTreatmentPlan mapper(PatientTreatmentPlanDraft patientTreatmentPlanDraft) {

        PatientTreatmentPlan patientTreatmentPlan = new PatientTreatmentPlan();

        patientTreatmentPlan.setId(new ObjectId(patientTreatmentPlanDraft.getTreatmentPlanID()));
        patientTreatmentPlan.setDoctorID(patientTreatmentPlanDraft.getDoctorID());
        patientTreatmentPlan.setClinicID(patientTreatmentPlanDraft.getClinicID());
        patientTreatmentPlan.setPatientID(patientTreatmentPlanDraft.getPatientID());
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

    public PatientTreatmentPlanDto DtoMapper(PatientTreatmentPlanDraft patientTreatmentPlanDraft) {
        PatientTreatmentPlanDto patientTreatmentPlan = new PatientTreatmentPlanDto();

        patientTreatmentPlan.setId(patientTreatmentPlanDraft.getTreatmentPlanID());
        patientTreatmentPlan.setDoctorID(patientTreatmentPlanDraft.getDoctorID());
        patientTreatmentPlan.setClinicID(patientTreatmentPlanDraft.getClinicID());
        patientTreatmentPlan.setPatientID(patientTreatmentPlanDraft.getPatientID());
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
