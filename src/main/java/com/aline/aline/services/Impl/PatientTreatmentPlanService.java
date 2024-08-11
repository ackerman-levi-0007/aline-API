package com.aline.aline.services.Impl;

import com.aline.aline.CustomMapper.PatientTreatmentPlanDraftMapper;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.dao.IPatientTreatmentPlanDao;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.services.IPatientTreatmentPlanService;
import com.aline.aline.services.helpers.PatientHelperService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientTreatmentPlanService implements IPatientTreatmentPlanService {

    private final PatientHelperService patientHelperService;
    private final IPatientTreatmentPlanDao patientTreatmentPlanDao;
    private final IPatientDentalDetailsMappingDao patientDentalDetailsMappingDao;
    private final PatientTreatmentPlanDraftMapper patientTreatmentPlanDraftMapper;

    @Override
    public PatientTreatmentPlan createTreatmentPlan(PatientTreatmentPlan patientTreatmentPlan) {
        patientHelperService.checkLoggedInUserPermissionForPatientIDDentalDetails(patientTreatmentPlan.getPatientID());
        PatientTreatmentPlan savedPatientTreatmentPlan = this.patientTreatmentPlanDao.createTreatmentPlan(patientTreatmentPlan);
        this.patientDentalDetailsMappingDao.addPatientTreatmentPlanIDForPatientID(
                savedPatientTreatmentPlan.getPatientID(),
                savedPatientTreatmentPlan.getId().toString()
        );
        return savedPatientTreatmentPlan;
    }

    @Override
    public PatientTreatmentPlan saveDraftForTreatmentPlan(PatientTreatmentPlan patientTreatmentPlan) {
        patientHelperService.checkLoggedInUserPermissionForPatientIDDentalDetails(patientTreatmentPlan.getPatientID());
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = this.patientTreatmentPlanDao.saveDraftForTreatmentPlan(patientTreatmentPlan);
        this.patientDentalDetailsMappingDao.addUnsavedDraftTreatmentPlanIDForPatientID(
            patientTreatmentPlanDraft.getPatientID(),
                patientTreatmentPlanDraft.getId().toString()
        );
        return this.patientTreatmentPlanDraftMapper.mapper(patientTreatmentPlanDraft);
    }

    @Override
    public void sendTreatmentPlanModificationToDoctor(String patientID, String treatmentPlanID, PatientTreatmentPlan patientTreatmentPlan) throws BadRequestException {
        try{
            PatientTreatmentPlan getpatientTreatmentPlan = this.patientTreatmentPlanDao.getTreatmentPlan(patientID, treatmentPlanID);
            this.patientTreatmentPlanDao.updateTreatmentPlan(patientID, treatmentPlanID, getpatientTreatmentPlan, patientTreatmentPlan);
            this.patientTreatmentPlanDao.moveTreatmentPlanToHistory(getpatientTreatmentPlan);
        } catch (ResourceNotFoundException ex){
            createTreatmentPlan(patientTreatmentPlan);
        }
    }

    @Override
    public PatientTreatmentPlan getTreatmentPlan(String patientID, String treatmentPlanID) {
        return this.patientTreatmentPlanDao.getTreatmentPlan(patientID, treatmentPlanID);
    }

    @Override
    public List<PatientTreatmentPlan> getAllTreatmentPlanForPatientID(String patientID) {
        return this.patientTreatmentPlanDao.getAllTreatmentPlanForPatientID(patientID);
    }

    @Override
    public PatientTreatmentPlan getTreatmentPlanDraft(String patientID, String treatmentPlanID) {
        return this.patientTreatmentPlanDao.getTreatmentPlanDraft(patientID, treatmentPlanID);
    }

    @Override
    public List<String> getAllHistoricalVersionIDsForTreatmentPlan(String patientID, String treatmentPlanID) {
        return this.patientTreatmentPlanDao.getAllHistoricalVersionIDsForTreatmentPlan(patientID, treatmentPlanID);
    }

    @Override
    public PatientTreatmentPlan getHistoricalTreatmentPlan(String patientID, String treatmentPlanID, String treatmentPlanVersionID) {
        return this.patientTreatmentPlanDao.getHistoricalTreatmentPlan(patientID, treatmentPlanID, treatmentPlanVersionID);
    }
}
