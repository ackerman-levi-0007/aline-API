package com.aline.aline.services.Impl;

import com.aline.aline.dao.IDentalDetailsDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import com.aline.aline.services.IPatientDentalDetailsService;
import com.aline.aline.services.helpers.PatientHelperService;
import com.aline.aline.utilities.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PatientDentalDetailsService implements IPatientDentalDetailsService {

    private final IDentalDetailsDao patientDentalDetailsDao;
    private final PatientHelperService patientHelperService;

    @Override
    public PatientPreviousDentalHistory createPreviousDentalHistoryDetails(int rebootID, PatientPreviousDentalHistory patientPreviousDentalHistoryDetails) {
        patientHelperService.checkLoggedInUserPermission(
                patientPreviousDentalHistoryDetails.getPatientID(), rebootID);
        return this.patientDentalDetailsDao.createPreviousDentalHistoryDetails(
                patientPreviousDentalHistoryDetails, rebootID);
    }

    @Override
    public PatientTreatmentGoal createPatientTreatmentGoal(int rebootID, PatientTreatmentGoal patientTreatmentGoal) {
        patientHelperService.checkLoggedInUserPermission(
                patientTreatmentGoal.getPatientID(), rebootID);
        return this.patientDentalDetailsDao.createPatientTreatmentGoal(patientTreatmentGoal, rebootID);
    }

    @Override
    public PatientDentalDetail createPatientDentalDetail(int rebootID, PatientDentalDetail patientDentalDetail) throws BadRequestException {
        validatePatientDentalDetail(patientDentalDetail, rebootID);
        return this.patientDentalDetailsDao.createPatientDentalDetail(patientDentalDetail, rebootID);
    }

    @Override
    public PatientPreviousDentalHistory updatePreviousDentalHistoryDetails(int rebootID, PatientPreviousDentalHistory patientPreviousDentalHistoryDetails) {
        patientHelperService.checkLoggedInUserPermission(
                patientPreviousDentalHistoryDetails.getPatientID(), rebootID);
        return this.patientDentalDetailsDao.updatePreviousDentalHistoryDetails(patientPreviousDentalHistoryDetails, rebootID);
    }

    @Override
    public PatientTreatmentGoal updatePatientTreatmentGoal(int rebootID, PatientTreatmentGoal patientTreatmentGoal) {
        patientHelperService.checkLoggedInUserPermission(
                patientTreatmentGoal.getPatientID(), rebootID);
        return this.patientDentalDetailsDao.updatePatientTreatmentGoal(patientTreatmentGoal, rebootID);
    }

    @Override
    public PatientDentalDetail updatePatientDentalDetail(int rebootID, PatientDentalDetail patientDentalDetail) throws BadRequestException {
        validatePatientDentalDetail(patientDentalDetail, rebootID);
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientDentalDetail.getPatientTreatmentGoal().getPatientID());
        return this.patientDentalDetailsDao.updatePatientDentalDetail(patientDentalDetail, rebootID);
    }

    @Override
    public Object getPreviousDentalHistoryDetailsByPatientID(String patientID, int rebootID) {
        patientHelperService.checkLoggedInUserPermission(patientID, rebootID);
        try{
            return this.patientDentalDetailsDao.getPreviousDentalHistoryDetails(
                    CommonUtils.getPatientPlanMapping().getPreviousDentalHistoryId()
            );
        }
        catch (ResourceNotFoundException ex){
            return new HashMap<>();
        }

    }

    @Override
    public Object getPatientTreatmentGoalByPatientID(String patientID, int rebootID) {
        patientHelperService.checkLoggedInUserPermission(patientID, rebootID);
        try{
            return this.patientDentalDetailsDao.getPatientTreatmentGoal(
                    CommonUtils.getPatientPlanMapping().getTreatmentGoalId()
            );
        }catch (ResourceNotFoundException ex){
            return new HashMap<>();
        }
    }

    @Override
    public Object getPatientDentalDetailByPatientID(String patientID, int rebootID) {
        patientHelperService.checkLoggedInUserPermission(patientID, rebootID);
        try {
            PatientDentalDetailsMapping patientDentalDetailsMapping = CommonUtils.getPatientPlanMapping();
            return this.patientDentalDetailsDao.getPatientDentalDetail(
                    patientDentalDetailsMapping.getPreviousDentalHistoryId(),
                    patientDentalDetailsMapping.getTreatmentGoalId()
            );
        }catch (ResourceNotFoundException ex){
            return new HashMap<>();
        }
    }

    /*****************************************************************************************
                                            Helpers
     ****************************************************************************************/

    public void validatePatientDentalDetail(PatientDentalDetail patientDentalDetail, int rebootID) throws BadRequestException {
        if(patientDentalDetail.getPatientPreviousDentalHistoryDetails().getPatientID() == null
                || patientDentalDetail.getPatientTreatmentGoal().getPatientID() == null){
            throw new BadRequestException("Please provide the patientID in the patient details");
        }
        else if(!patientDentalDetail.getPatientPreviousDentalHistoryDetails().getPatientID().equals(patientDentalDetail.getPatientTreatmentGoal().getPatientID())) {
            throw new BadRequestException("The patientID is not similar in all the patient details object. Please check !!!");
        }

        patientHelperService.checkLoggedInUserPermission(
                patientDentalDetail.getPatientPreviousDentalHistoryDetails().getPatientID(), rebootID);
    }
}
