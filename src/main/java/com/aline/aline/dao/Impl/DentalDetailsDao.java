package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IDentalDetailsDao;
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

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DentalDetailsDao implements IDentalDetailsDao {

    private final PatientPreviousDentalHistoryRepo patientPreviousDentalHistoryRepo;
    private final PatientTreatmentGoalRepo patientTreatmentGoalRepo;
    private final DentalDetailsMappingDao patientDentalDetailsMappingDao;

    @Override
    public PatientPreviousDentalHistory createPreviousDentalHistoryDetails(
            PatientPreviousDentalHistory patientPreviousDentalHistoryDetails,
            int rebootID
    ) {
        if(checkPreviousDentalHistoryExistsForPatientID(patientPreviousDentalHistoryDetails.getPatientID())){
            throw new EntityExistsException("Previous dental history already exists for the patient!!!");
        }
        PatientPreviousDentalHistory savedPreviousDentalHistory =
                this.patientPreviousDentalHistoryRepo.save(patientPreviousDentalHistoryDetails);

        this.patientDentalDetailsMappingDao.updatePatientPreviousDentalHistoryDetailsID(
                savedPreviousDentalHistory.getPatientID(),
                savedPreviousDentalHistory.getId().toString(),
                rebootID
        );

        return savedPreviousDentalHistory;
    }

    @Override
    public PatientTreatmentGoal createPatientTreatmentGoal(
            PatientTreatmentGoal patientTreatmentGoal,
            int rebootID
    ) {
        if(checkTreatmentGoalExistsForPatientID(patientTreatmentGoal.getPatientID())){
            throw new EntityExistsException("Treatment goal already exists for the patient!!!");
        }
        PatientTreatmentGoal savedTreatmentGoal = this.patientTreatmentGoalRepo.save(patientTreatmentGoal);

        this.patientDentalDetailsMappingDao.updatePatientTreatmentGoalID(
                savedTreatmentGoal.getPatientID(),
                savedTreatmentGoal.getId().toString(),
                rebootID
        );

        return savedTreatmentGoal;
    }

    @Override
    public PatientDentalDetail createPatientDentalDetail(
            PatientDentalDetail patientDentalDetail,
            int rebootID
    ) {

        PatientPreviousDentalHistory patientPreviousDentalHistory = this.patientPreviousDentalHistoryRepo.save(patientDentalDetail.getPatientPreviousDentalHistoryDetails());
        PatientTreatmentGoal patientTreatmentGoal = this.patientTreatmentGoalRepo.save(patientDentalDetail.getPatientTreatmentGoal());

        return new PatientDentalDetail(patientPreviousDentalHistory, patientTreatmentGoal);
    }

    @Override
    public PatientPreviousDentalHistory updatePreviousDentalHistoryDetails(
            PatientPreviousDentalHistory patientPreviousDentalHistoryDetails,
            int rebootID
    ) {
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
            return createPreviousDentalHistoryDetails(patientPreviousDentalHistoryDetails, rebootID);
        }
    }

    @Override
    public PatientTreatmentGoal updatePatientTreatmentGoal(
            PatientTreatmentGoal patientTreatmentGoal,
            int rebootID
    ) {
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
            return createPatientTreatmentGoal(patientTreatmentGoal, rebootID);
        }
    }

    @Override
    public PatientDentalDetail updatePatientDentalDetail(
            PatientDentalDetail patientDentalDetail,
            int rebootID
    ) {
        PatientPreviousDentalHistory patientPreviousDentalHistory = updatePreviousDentalHistoryDetails(patientDentalDetail.getPatientPreviousDentalHistoryDetails(), rebootID);
        PatientTreatmentGoal patientTreatmentGoal = updatePatientTreatmentGoal(patientDentalDetail.getPatientTreatmentGoal(), rebootID);

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

    @Override
    public PatientDentalDetail cloneLatestDetails(String patientID, String previousDentalHistoryID, String treatmentGoalID) {
        PatientPreviousDentalHistory patientPreviousDentalHistory;
        PatientTreatmentGoal patientTreatmentGoal;
        try{
            patientPreviousDentalHistory = getPreviousDentalHistoryDetails(previousDentalHistoryID);
            patientPreviousDentalHistory.setId(null);

            patientTreatmentGoal = getPatientTreatmentGoal(treatmentGoalID);
            patientTreatmentGoal.setId(null);

            PatientPreviousDentalHistory savedPreviousDentalHistory = this.patientPreviousDentalHistoryRepo.save(patientPreviousDentalHistory);
            PatientTreatmentGoal savedTreatmentGoal = this.patientTreatmentGoalRepo.save(patientTreatmentGoal);

            return new PatientDentalDetail(savedPreviousDentalHistory, savedTreatmentGoal);
        }
        catch (ResourceNotFoundException ex){
            //Do nothing
        }
        return null;
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
