package com.aline.aline.services.Impl;

import com.aline.aline.dao.IPatientDentalDetailsDao;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import com.aline.aline.services.IPatientDentalDetailsService;
import com.aline.aline.services.helpers.PatientHelperService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PatientDentalDetailsService implements IPatientDentalDetailsService {

    private final IPatientDentalDetailsDao patientDentalDetailsDao;
    private final PatientHelperService patientHelperService;
    private final IPatientDentalDetailsMappingDao patientDentalDetailsMappingDao;

    @Override
    public PatientPreviousDentalHistory createPreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientPreviousDentalHistoryDetails.getPatientID());
        PatientPreviousDentalHistory patientPreviousDentalHistory = this.patientDentalDetailsDao.createPreviousDentalHistoryDetails(patientPreviousDentalHistoryDetails);
        this.patientDentalDetailsMappingDao.updatePatientPreviousDentalHistoryDetailsID(
                patientPreviousDentalHistory.getPatientID(),
                patientPreviousDentalHistory.getId().toString(),
                0
        );
        return patientPreviousDentalHistory;
    }

    @Override
    public PatientTreatmentGoal createPatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientTreatmentGoal.getPatientID());
        PatientTreatmentGoal savedPatientTreatmentGoal = this.patientDentalDetailsDao.createPatientTreatmentGoal(patientTreatmentGoal);
        this.patientDentalDetailsMappingDao.updatePatientTreatmentGoalID(
                savedPatientTreatmentGoal.getPatientID(),
                savedPatientTreatmentGoal.getId().toString(),
                0
        );
        return savedPatientTreatmentGoal;
    }

    @Override
    public PatientDentalDetail createPatientDentalDetail(PatientDentalDetail patientDentalDetail) throws BadRequestException {
        validatePatientDentalDetail(patientDentalDetail);
        PatientDentalDetail savedPatientDentalDetail = this.patientDentalDetailsDao.createPatientDentalDetail(patientDentalDetail);
        this.patientDentalDetailsMappingDao.updatePatientDentalDetailsID(
                savedPatientDentalDetail.getPatientTreatmentGoal().getPatientID(),
                savedPatientDentalDetail.getPatientPreviousDentalHistoryDetails().getId().toString(),
                savedPatientDentalDetail.getPatientTreatmentGoal().getId().toString(),
                0
        );
        return savedPatientDentalDetail;
    }

    @Override
    public PatientPreviousDentalHistory updatePreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientPreviousDentalHistoryDetails.getPatientID());
        return this.patientDentalDetailsDao.updatePreviousDentalHistoryDetails(patientPreviousDentalHistoryDetails);
    }

    @Override
    public PatientTreatmentGoal updatePatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientTreatmentGoal.getPatientID());
        return this.patientDentalDetailsDao.updatePatientTreatmentGoal(patientTreatmentGoal);
    }

    @Override
    public PatientDentalDetail updatePatientDentalDetail(PatientDentalDetail patientDentalDetail) throws BadRequestException {
        validatePatientDentalDetail(patientDentalDetail);
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientDentalDetail.getPatientTreatmentGoal().getPatientID());
        return this.patientDentalDetailsDao.updatePatientDentalDetail(patientDentalDetail);
    }

    @Override
    public Object getPreviousDentalHistoryDetailsByPatientID(String patientID) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        try{
            return this.patientDentalDetailsDao.getPreviousDentalHistoryDetailsByPatientID(patientID);
        }
        catch (ResourceNotFoundException ex){
            return new HashMap<>();
        }

    }

    @Override
    public Object getPatientTreatmentGoalByPatientID(String patientID) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        try{
            return this.patientDentalDetailsDao.getPatientTreatmentGoalByPatientID(patientID);
        }catch (ResourceNotFoundException ex){
            return new HashMap<>();
        }
    }

    @Override
    public Object getPatientDentalDetailByPatientID(String patientID) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        try {
            return this.patientDentalDetailsDao.getPatientDentalDetailByPatientID(patientID);
        }catch (ResourceNotFoundException ex){
            return new HashMap<>();
        }
    }

    @Override
    public void deletePreviousDentalHistoryDetailsByPatientID(String patientID) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        this.patientDentalDetailsDao.deletePreviousDentalHistoryDetailsByPatientID(patientID);
    }

    @Override
    public void deletePatientTreatmentGoalByPatientID(String patientID) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        this.patientDentalDetailsDao.deletePatientTreatmentGoalByPatientID(patientID);
    }

    @Override
    public void deletePatientDentalDetailByPatientID(String patientID) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        this.patientDentalDetailsDao.deletePatientDentalDetailByPatientID(patientID);
    }

    /*****************************************************************************************
                                            Helpers
     ****************************************************************************************/

    public void validatePatientDentalDetail(PatientDentalDetail patientDentalDetail) throws BadRequestException {
        if(patientDentalDetail.getPatientPreviousDentalHistoryDetails().getPatientID() == null
                || patientDentalDetail.getPatientTreatmentGoal().getPatientID() == null){
            throw new BadRequestException("Please provide the patientID in the patient details");
        }
        else if(!patientDentalDetail.getPatientPreviousDentalHistoryDetails().getPatientID().equals(patientDentalDetail.getPatientTreatmentGoal().getPatientID())) {
            throw new BadRequestException("The patientID is not similar in all the patient details object. Please check !!!");
        }

        patientHelperService.checkLoggedInUserPermissionForPatientID(patientDentalDetail.getPatientPreviousDentalHistoryDetails().getPatientID());
    }
}
