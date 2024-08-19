package com.aline.aline.dao.Impl;

import com.aline.aline.CustomMapper.PatientTreatmentPlanDraftMapper;
import com.aline.aline.CustomMapper.PatientTreatmentPlanHistoryMapper;
import com.aline.aline.dao.IPatientTreatmentPlanDao;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import com.aline.aline.entities.User;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ForbiddenException;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanDraftRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanHistoryRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanRepo;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientTreatmentPlanDao implements IPatientTreatmentPlanDao {

    private final PatientTreatmentPlanRepo patientTreatmentPlanRepo;
    private final PatientTreatmentPlanDraftRepo patientTreatmentPlanDraftRepo;
    private final PatientTreatmentPlanHistoryRepo patientTreatmentPlanHistoryRepo;
    private final PatientTreatmentPlanDraftMapper patientTreatmentPlanDraftMapper;
    private final PatientTreatmentPlanHistoryMapper patientTreatmentPlanHistoryMapper;

    @Override
    public PatientTreatmentPlan createTreatmentPlan(PatientTreatmentPlan patientTreatmentPlan) {
        return this.patientTreatmentPlanRepo.save(patientTreatmentPlan);
    }

    @Override
    public PatientTreatmentPlanDraft saveDraftForTreatmentPlan(PatientTreatmentPlan patientTreatmentPlan) {
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = this.patientTreatmentPlanDraftMapper.mapper(patientTreatmentPlan);
        return this.patientTreatmentPlanDraftRepo.save(patientTreatmentPlanDraft);
    }

    @Override
    public PatientTreatmentPlan getTreatmentPlan(String patientID, String treatmentPlanID) {
        return this.patientTreatmentPlanRepo.findByIdAndPatientID(treatmentPlanID, patientID)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment Plan", "treatmentPlanID", treatmentPlanID)
                );
    }

    @Override
    public List<PatientTreatmentPlan> getAllTreatmentPlanForPatientID(String patientID) {
        return this.patientTreatmentPlanRepo.findByPatientID(patientID)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment plan", "patientID", patientID));
    }

    @Override
    public PatientTreatmentPlan getTreatmentPlanDraft(String patientID, String treatmentPlanID) {
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = getPatientTreatmentPlanDraft(patientID, treatmentPlanID);
        return this.patientTreatmentPlanDraftMapper.mapper(patientTreatmentPlanDraft);
    }

    @Override
    public List<PatientTreatmentPlanHistory> getAllHistoricalVersionIDsForTreatmentPlan(String patientID, String treatmentPlanID) {
        return this.patientTreatmentPlanHistoryRepo.findByPatientIDAndTreatmentPlanID(patientID, treatmentPlanID);
    }

    @Override
    public PatientTreatmentPlan getHistoricalTreatmentPlan(String patientID, String treatmentPlanID, String treatmentPlanVersionID) {
        PatientTreatmentPlanHistory patientTreatmentPlanHistory = this.patientTreatmentPlanHistoryRepo
                .findByIdAndPatientIDAndTreatmentPlanID(treatmentPlanVersionID, patientID, treatmentPlanID)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Treatment plan history", "treatmentPlanVersionID", treatmentPlanVersionID)
                );
        return this.patientTreatmentPlanHistoryMapper.mapper(patientTreatmentPlanHistory);
    }

    @Override
    public void updateTreatmentPlan(String patientID, String treatmentPlanID, PatientTreatmentPlan currentTreatmentPlan, PatientTreatmentPlan updatedTreatmentPlan) throws BadRequestException {
        if(currentTreatmentPlan == null){
            currentTreatmentPlan = getTreatmentPlan(patientID, treatmentPlanID);
        }

        if(checkPatientTreatmentPlan(currentTreatmentPlan, updatedTreatmentPlan)){
            if(compareTreatmentPlan(currentTreatmentPlan, updatedTreatmentPlan)){

                updatedTreatmentPlan.setCreatedOn(currentTreatmentPlan.getCreatedOn());

                this.patientTreatmentPlanRepo.save(updatedTreatmentPlan);
            }
            else{
                throw new BadRequestException("The updated details are the same as the current treatment plan");
            }
        }else{
            throw new BadRequestException("The provided details are not correct for treatment plan");
        }
    }

    @Override
    public void moveTreatmentPlanToHistory(PatientTreatmentPlan patientTreatmentPlan) {
        PatientTreatmentPlanHistory patientTreatmentPlanHistory = this.patientTreatmentPlanHistoryMapper.mapper(patientTreatmentPlan);
        this.patientTreatmentPlanHistoryRepo.save(patientTreatmentPlanHistory);
    }

    /*****************************************************************************************
                                            Helpers
    *****************************************************************************************/

    private PatientTreatmentPlanDraft getPatientTreatmentPlanDraft(String patientID, String treatmentPlanID){
        User loggedInUser = SecurityUtils.getLoggedInUser();

        PatientTreatmentPlanDraft patientTreatmentPlanDraft;

        if(loggedInUser.getRole().contains(UserRole.ROLE_ADMIN) || loggedInUser.getRole().contains(UserRole.ROLE_LAB)){
            patientTreatmentPlanDraft = getPatientTreatmentPlanDraftByID(patientID, treatmentPlanID);
        } else if (loggedInUser.getRole().contains(UserRole.ROLE_CLINIC)) {
            patientTreatmentPlanDraft = getPatientTreatmentPlanDraftByClinicID(patientID, treatmentPlanID, loggedInUser.getId().toString());
        } else if (loggedInUser.getRole().contains(UserRole.ROLE_DOCTOR)) {
            patientTreatmentPlanDraft = getPatientTreatmentPlanDraftByDoctorID(patientID, treatmentPlanID, loggedInUser.getId().toString());
        } else {
            throw new ForbiddenException("Patient Draft : User does not have access to view the data. Please contact with administrator.");
        }

        return patientTreatmentPlanDraft;
    }

    private PatientTreatmentPlanDraft getPatientTreatmentPlanDraftByID(String patientID, String treatmentPlanID) {
        Optional<PatientTreatmentPlanDraft> patientTreatmentPlanDraft = this.patientTreatmentPlanDraftRepo
                .findByPatientIDAndTreatmentPlanID(patientID, treatmentPlanID);

        return patientTreatmentPlanDraft.isPresent() ? patientTreatmentPlanDraft.get() : new PatientTreatmentPlanDraft();
    }

    private PatientTreatmentPlanDraft getPatientTreatmentPlanDraftByDoctorID(
            String patientID, String treatmentPlanID, String doctorID
    ) {
        Optional<PatientTreatmentPlanDraft> patientTreatmentPlanDraft = this.patientTreatmentPlanDraftRepo
                .findByPatientIDAndTreatmentPlanIDAndDoctorID(patientID, treatmentPlanID, doctorID);

        return patientTreatmentPlanDraft.isPresent() ? patientTreatmentPlanDraft.get() : new PatientTreatmentPlanDraft();
    }

    private PatientTreatmentPlanDraft getPatientTreatmentPlanDraftByClinicID(
            String patientID, String treatmentPlanID, String clinicID
    ) {
        Optional<PatientTreatmentPlanDraft> patientTreatmentPlanDraft = this.patientTreatmentPlanDraftRepo
                .findByPatientIDAndTreatmentPlanIDAndClinicID(patientID, treatmentPlanID,clinicID);

        return patientTreatmentPlanDraft.isPresent() ? patientTreatmentPlanDraft.get() : new PatientTreatmentPlanDraft();
    }

    private boolean checkPatientTreatmentPlan(PatientTreatmentPlan currentTreatmentPlan, PatientTreatmentPlan updatedTreatmentPlan) {
        return true;
    }

    private boolean compareTreatmentPlan(PatientTreatmentPlan currentTreatmentPlan, PatientTreatmentPlan updatedTreatmentPlan) {
        return true;
    }
}
