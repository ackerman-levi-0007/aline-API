package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IPatientDentalDetailsDao;
import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import com.aline.aline.repositories.PatientPreviousDentalHistoryRepo;
import com.aline.aline.repositories.PatientTreatmentGoalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PatientDentalDetailsDao implements IPatientDentalDetailsDao {

    private final PatientPreviousDentalHistoryRepo patientPreviousDentalHistoryRepo;
    private final PatientTreatmentGoalRepo patientTreatmentGoalRepo;

    @Override
    public PatientPreviousDentalHistory createPreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails) {
        return this.patientPreviousDentalHistoryRepo.save(patientPreviousDentalHistoryDetails);
    }

    @Override
    public PatientTreatmentGoal createPatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal) {
        return this.patientTreatmentGoalRepo.save(patientTreatmentGoal);
    }

    @Override
    public PatientDentalDetail createPatientDentalDetail(PatientDentalDetail patientDentalDetail) {

        PatientPreviousDentalHistory patientPreviousDentalHistory = this.patientPreviousDentalHistoryRepo.save(patientDentalDetail.getPatientPreviousDentalHistoryDetails());
        PatientTreatmentGoal patientTreatmentGoal = this.patientTreatmentGoalRepo.save(patientDentalDetail.getPatientTreatmentGoal());

        return new PatientDentalDetail(patientPreviousDentalHistory, patientTreatmentGoal);
    }

    @Override
    public PatientPreviousDentalHistory updatePreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails) {
        PatientPreviousDentalHistory fetchedPatientPreviousDentalHistory = getPreviousDentalHistoryDetailsByPatientID(patientPreviousDentalHistoryDetails.getPatientID());

        fetchedPatientPreviousDentalHistory.setChiefComplaint(patientPreviousDentalHistoryDetails.getChiefComplaint());
        fetchedPatientPreviousDentalHistory.setCrownsBridges(patientPreviousDentalHistoryDetails.getCrownsBridges());
        fetchedPatientPreviousDentalHistory.setImplants(patientPreviousDentalHistoryDetails.getImplants());
        fetchedPatientPreviousDentalHistory.setVeneers(patientPreviousDentalHistoryDetails.getVeneers());
        fetchedPatientPreviousDentalHistory.setPreviousTreatment(patientPreviousDentalHistoryDetails.getPreviousTreatment());
        fetchedPatientPreviousDentalHistory.setComposites(patientPreviousDentalHistoryDetails.getComposites());
        fetchedPatientPreviousDentalHistory.setHistoryOthers(patientPreviousDentalHistoryDetails.getHistoryOthers());

        return this.patientPreviousDentalHistoryRepo.save(fetchedPatientPreviousDentalHistory);
    }

    @Override
    public PatientTreatmentGoal updatePatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal) {
        PatientTreatmentGoal fetchedPatientTreatmentGoal = getPatientTreatmentGoalByPatientID(patientTreatmentGoal.getPatientID());

        fetchedPatientTreatmentGoal.setCorrection(patientTreatmentGoal.getCorrection());
        fetchedPatientTreatmentGoal.setArches(patientTreatmentGoal.getArches());
        fetchedPatientTreatmentGoal.setIpr(patientTreatmentGoal.getIpr());
        fetchedPatientTreatmentGoal.setIprDetails(patientTreatmentGoal.getIprDetails());
        fetchedPatientTreatmentGoal.setAttachments(patientTreatmentGoal.getAttachments());
        fetchedPatientTreatmentGoal.setAttachmentsDetails(patientTreatmentGoal.getAttachmentsDetails());
        fetchedPatientTreatmentGoal.setTreatmentGoalOthers(patientTreatmentGoal.getTreatmentGoalOthers());

        return this.patientTreatmentGoalRepo.save(fetchedPatientTreatmentGoal);
    }

    @Override
    public PatientDentalDetail updatePatientDentalDetail(PatientDentalDetail patientDentalDetail) {
        PatientPreviousDentalHistory patientPreviousDentalHistory = updatePreviousDentalHistoryDetails(patientDentalDetail.getPatientPreviousDentalHistoryDetails());
        PatientTreatmentGoal patientTreatmentGoal = updatePatientTreatmentGoal(patientDentalDetail.getPatientTreatmentGoal());

        return new PatientDentalDetail(patientPreviousDentalHistory, patientTreatmentGoal);
    }

    @Override
    public PatientPreviousDentalHistory getPreviousDentalHistoryDetailsByPatientID(String patientID) {
        return this.patientPreviousDentalHistoryRepo.findByPatientID(patientID)
                .orElseThrow(() -> new ResourceNotFoundException("Previous dental history", "patientID", patientID));
    }

    @Override
    public PatientTreatmentGoal getPatientTreatmentGoalByPatientID(String patientID) {
        return this.patientTreatmentGoalRepo.findByPatientID(patientID).orElseThrow(
                () -> new ResourceNotFoundException("Patient treatment goal", "patientID", patientID)
        );
    }

    @Override
    public PatientDentalDetail getPatientDentalDetailByPatientID(String patientID) {
        PatientPreviousDentalHistory patientPreviousDentalHistory = getPreviousDentalHistoryDetailsByPatientID(patientID);
        PatientTreatmentGoal patientTreatmentGoal = getPatientTreatmentGoalByPatientID(patientID);

        return new PatientDentalDetail(patientPreviousDentalHistory, patientTreatmentGoal);
    }

    /*****************************************************************************************
                                            Helpers
     *****************************************************************************************/
}