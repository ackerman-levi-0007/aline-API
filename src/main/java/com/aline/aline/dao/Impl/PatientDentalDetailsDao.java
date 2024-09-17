package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IPatientDentalDetailsDao;
import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import com.aline.aline.repositories.PatientPreviousDentalHistoryRepo;
import com.aline.aline.repositories.PatientTreatmentGoalRepo;
import com.aline.aline.utilities.CommonUtils;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientDentalDetailsDao implements IPatientDentalDetailsDao {

    private final PatientPreviousDentalHistoryRepo patientPreviousDentalHistoryRepo;
    private final PatientTreatmentGoalRepo patientTreatmentGoalRepo;

    @Override
    public PatientPreviousDentalHistory createPreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails) {
        if(checkPreviousDentalHistoryExistsForPatientID(patientPreviousDentalHistoryDetails.getPatientID())){
            throw new EntityExistsException("Previous dental history already exists for the patient!!!");
        }
        return this.patientPreviousDentalHistoryRepo.save(patientPreviousDentalHistoryDetails);
    }

    @Override
    public PatientTreatmentGoal createPatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal) {
        if(checkTreatmentGoalExistsForPatientID(patientTreatmentGoal.getPatientID())){
            throw new EntityExistsException("Treatment goal already exists for the patient!!!");
        }
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
        Optional<PatientPreviousDentalHistory> fetchedPatientPreviousDentalHistoryOptional =
                this.patientPreviousDentalHistoryRepo.findByPatientID(patientPreviousDentalHistoryDetails.getPatientID());

        if(fetchedPatientPreviousDentalHistoryOptional.isPresent()){
            PatientPreviousDentalHistory fetchedPatientPreviousDentalHistory = fetchedPatientPreviousDentalHistoryOptional.get();

            fetchedPatientPreviousDentalHistory.setChiefComplaint(patientPreviousDentalHistoryDetails.getChiefComplaint());
            fetchedPatientPreviousDentalHistory.setCrownsBridges(patientPreviousDentalHistoryDetails.getCrownsBridges());
            fetchedPatientPreviousDentalHistory.setImplants(patientPreviousDentalHistoryDetails.getImplants());
            fetchedPatientPreviousDentalHistory.setVeneers(patientPreviousDentalHistoryDetails.getVeneers());
            fetchedPatientPreviousDentalHistory.setPreviousTreatment(patientPreviousDentalHistoryDetails.getPreviousTreatment());
            fetchedPatientPreviousDentalHistory.setComposites(patientPreviousDentalHistoryDetails.getComposites());
            fetchedPatientPreviousDentalHistory.setHistoryOthers(patientPreviousDentalHistoryDetails.getHistoryOthers());

            return this.patientPreviousDentalHistoryRepo.save(fetchedPatientPreviousDentalHistory);
        }else{
            return createPreviousDentalHistoryDetails(patientPreviousDentalHistoryDetails);
        }
    }

    @Override
    public PatientTreatmentGoal updatePatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal) {
        Optional<PatientTreatmentGoal> fetchedPatientTreatmentGoalOptional =
                this.patientTreatmentGoalRepo.findByPatientID(patientTreatmentGoal.getPatientID());

        if(fetchedPatientTreatmentGoalOptional.isPresent()) {
            PatientTreatmentGoal fetchedPatientTreatmentGoal = fetchedPatientTreatmentGoalOptional.get();

            fetchedPatientTreatmentGoal.setCorrection(patientTreatmentGoal.getCorrection());
            fetchedPatientTreatmentGoal.setArches(patientTreatmentGoal.getArches());
            fetchedPatientTreatmentGoal.setIpr(patientTreatmentGoal.getIpr());
            fetchedPatientTreatmentGoal.setIprDetails(patientTreatmentGoal.getIprDetails());
            fetchedPatientTreatmentGoal.setAttachments(patientTreatmentGoal.getAttachments());
            fetchedPatientTreatmentGoal.setAttachmentsDetails(patientTreatmentGoal.getAttachmentsDetails());
            fetchedPatientTreatmentGoal.setTreatmentGoalOthers(patientTreatmentGoal.getTreatmentGoalOthers());

            return this.patientTreatmentGoalRepo.save(fetchedPatientTreatmentGoal);
        }else{
            return createPatientTreatmentGoal(patientTreatmentGoal);
        }
    }

    @Override
    public PatientDentalDetail updatePatientDentalDetail(PatientDentalDetail patientDentalDetail) {
        PatientPreviousDentalHistory patientPreviousDentalHistory = updatePreviousDentalHistoryDetails(patientDentalDetail.getPatientPreviousDentalHistoryDetails());
        PatientTreatmentGoal patientTreatmentGoal = updatePatientTreatmentGoal(patientDentalDetail.getPatientTreatmentGoal());

        return new PatientDentalDetail(patientPreviousDentalHistory, patientTreatmentGoal);
    }

    @Override
    public PatientDentalDetail getPatientDentalDetail(String previousDentalHistoryId, String treatmentGoalId) {
        if(CommonUtils.isNullOrEmpty(previousDentalHistoryId) || CommonUtils.isNullOrEmpty(treatmentGoalId)){
            throw new ResourceNotFoundException("Patient dental details not found");
        }

        PatientPreviousDentalHistory patientPreviousDentalHistory = getPreviousDentalHistoryDetails(previousDentalHistoryId);
        PatientTreatmentGoal patientTreatmentGoal = getPatientTreatmentGoal(treatmentGoalId);

        return new PatientDentalDetail(patientPreviousDentalHistory, patientTreatmentGoal);
    }

    @Override
    public void deletePreviousDentalHistoryDetailsByPatientID(String patientID) {
        Optional<PatientPreviousDentalHistory> patientPreviousDentalHistory =
                this.patientPreviousDentalHistoryRepo.findByPatientID(patientID);
        patientPreviousDentalHistory.ifPresent(this.patientPreviousDentalHistoryRepo::delete);
    }

    @Override
    public void deletePatientTreatmentGoalByPatientID(String patientID) {
        Optional<PatientTreatmentGoal> patientTreatmentGoal =
                this.patientTreatmentGoalRepo.findByPatientID(patientID);
        patientTreatmentGoal.ifPresent(this.patientTreatmentGoalRepo::delete);
    }

    @Override
    public void deletePatientDentalDetailByPatientID(String patientID) {
        deletePreviousDentalHistoryDetailsByPatientID(patientID);
        deletePatientTreatmentGoalByPatientID(patientID);
    }

    @Override
    public PatientPreviousDentalHistory getPreviousDentalHistoryDetails(String previousDentalHistoryId) {
        return this.patientPreviousDentalHistoryRepo.findById(previousDentalHistoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Previous dental history","id", previousDentalHistoryId));
    }

    @Override
    public PatientTreatmentGoal getPatientTreatmentGoal(String treatmentGoalId) {
        return this.patientTreatmentGoalRepo.findById(treatmentGoalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient treatment goal", "id", treatmentGoalId));
    }

    /*****************************************************************************************
                                            Helpers
     *****************************************************************************************/
    public boolean checkPreviousDentalHistoryExistsForPatientID(String patientID){
        Optional<PatientPreviousDentalHistory> patientPreviousDentalHistory =
                this.patientPreviousDentalHistoryRepo.findByPatientID(patientID);
        return patientPreviousDentalHistory.isPresent();
    }

    public boolean checkTreatmentGoalExistsForPatientID(String patientID){
        Optional<PatientTreatmentGoal> patientTreatmentGoal =
                this.patientTreatmentGoalRepo.findByPatientID(patientID);
        return patientTreatmentGoal.isPresent();
    }
}
