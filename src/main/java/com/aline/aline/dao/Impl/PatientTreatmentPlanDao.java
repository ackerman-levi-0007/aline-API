package com.aline.aline.dao.Impl;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanListObject;
import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.CustomMapper.PatientTreatmentPlanDraftMapper;
import com.aline.aline.CustomMapper.PatientTreatmentPlanHistoryMapper;
import com.aline.aline.CustomMapper.PatientTreatmentPlanMapper;
import com.aline.aline.dao.IPatientTreatmentPlanDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import com.aline.aline.entities.User;
import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ForbiddenException;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import com.aline.aline.repositories.PatientDentalDetailsMappingRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanDraftRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanHistoryRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanRepo;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    private final PatientTreatmentPlanMapper patientTreatmentPlanMapper;
    private final PatientDentalDetailsMappingDao patientDentalDetailsMappingDao;
    private final PatientDentalDetailsMappingRepo patientDentalDetailsMappingRepo;


    private PatientTreatmentPlan createPlan(PatientTreatmentPlanDraft patientTreatmentPlanDraft) {
        PatientTreatmentPlan patientTreatmentPlan = this.patientTreatmentPlanMapper.DraftMapper(patientTreatmentPlanDraft);

        PatientTreatmentPlan savedPlan = this.patientTreatmentPlanRepo.save(patientTreatmentPlan);

        patientTreatmentPlanDraft.setTreatmentPlanID(savedPlan.getId().toString());
        this.patientTreatmentPlanDraftRepo.save(patientTreatmentPlanDraft);

        return savedPlan;
    }

    @Override
    public PatientTreatmentPlanDraft saveDraft(PatientTreatmentPlan patientTreatmentPlan) {
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = this.patientTreatmentPlanDraftMapper.mapper(patientTreatmentPlan);
        return this.patientTreatmentPlanDraftRepo.save(patientTreatmentPlanDraft);
    }

    @Override
    public PatientTreatmentPlan getTreatmentPlan(String patientID, String id) {
        return this.patientTreatmentPlanRepo.findByIdAndPatientID(id, patientID)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment Plan", "id", id)
                );
    }

    @Override
    public List<PatientTreatmentPlan> getAllTreatmentPlanForPatientID(String patientID) {
        return this.patientTreatmentPlanRepo.findByPatientID(patientID)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment plan", "patientID", patientID));
    }

    @Override
    public PatientTreatmentPlanDto getTreatmentPlanDraft(String patientID, String id) {
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = getPatientTreatmentPlanDraft(patientID, id);
        return this.patientTreatmentPlanDraftMapper.DtoMapper(patientTreatmentPlanDraft);
    }

    @Override
    public List<PatientTreatmentPlanHistory> getAllHistoricalVersionIDsForTreatmentPlan(String patientID, String treatmentPlanID) {
        return this.patientTreatmentPlanHistoryRepo.findByPatientIDAndTreatmentPlanID(patientID, treatmentPlanID);
    }

    @Override
    public PatientTreatmentPlanDto getHistoricalTreatmentPlan(String patientID, String id) {
        PatientTreatmentPlanHistory patientTreatmentPlanHistory = this.patientTreatmentPlanHistoryRepo
                .findByIdAndPatientID(id, patientID)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Treatment plan history", "id", id)
                );
        return this.patientTreatmentPlanHistoryMapper.DtoMapper(patientTreatmentPlanHistory);
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

    @Override
    public void updateDraft(String patientID, int rebootID, PatientTreatmentPlanDto patientTreatmentPlanDto) {
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = getPatientTreatmentPlanDraft(patientID, patientTreatmentPlanDto.getId());

        PatientTreatmentPlanDraft updatedDraft = this.patientTreatmentPlanDraftMapper
                .DraftDtoSetter(patientTreatmentPlanDraft, patientTreatmentPlanDto);

        this.patientTreatmentPlanDraftRepo.save(updatedDraft);
    }

    @Override
    public void savePlan(String patientID, int rebootID, String draftID) {
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = getPatientTreatmentPlanDraft(patientID, draftID);

        String id, historyID = null;

        if(patientTreatmentPlanDraft.getTreatmentPlanID() == null){
            PatientTreatmentPlan savedPlan = createPlan(patientTreatmentPlanDraft);
            id = savedPlan.getId().toString();
        }
        else{
            PatientTreatmentPlanHistory historyPlan = updatePlan(patientTreatmentPlanDraft);
            id = historyPlan.getTreatmentPlanID();
            historyID = historyPlan.getId().toString();
        }

        updatePlanMapping(patientID, rebootID, id, historyID);
    }

    private PatientTreatmentPlanHistory updatePlan(PatientTreatmentPlanDraft patientTreatmentPlanDraft) {
        PatientTreatmentPlan patientTreatmentPlan = getPlan(patientTreatmentPlanDraft.getTreatmentPlanID());

        PatientTreatmentPlan updatedPlan = this.patientTreatmentPlanMapper.PlanDraftSetter(patientTreatmentPlan, patientTreatmentPlanDraft);

        this.patientTreatmentPlanRepo.save(updatedPlan);
        PatientTreatmentPlanHistory patientTreatmentPlanHistory = this.patientTreatmentPlanHistoryMapper
                .mapper(patientTreatmentPlan);

        return this.patientTreatmentPlanHistoryRepo.save(patientTreatmentPlanHistory);
    }

    private void updatePlanMapping(String patientID, int rebootID, String id, String historyID) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = this.patientDentalDetailsMappingDao
                .getPatientDentalDetailsMapping(patientID, rebootID);

        TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
        treatmentPlanObject.setId(id);
        treatmentPlanObject.setStatus(TreatmentPlanStatus.shared);

        if(patientDentalDetailsMapping.getTreatmentPlanLatest() == null){
            this.patientDentalDetailsMappingDao.addPatientTreatmentPlanID(
                    patientID,
                    treatmentPlanObject,
                    0
            );
        }
        else{
            TreatmentPlanListObject historyObject = new TreatmentPlanListObject();

            List<TreatmentPlanObject> historyPlanObjects = new ArrayList<>();

            List<TreatmentPlanObject> currentPlanObjects = patientDentalDetailsMapping
                    .getTreatmentPlanLatest().getTreatmentPlans();

            patientDentalDetailsMapping.getTreatmentPlanLatest()
                    .getTreatmentPlans().forEach(
                            treatmentPlanObjectItr -> {
                                if(id != treatmentPlanObjectItr.getId()){
                                    if(treatmentPlanObjectItr.getHistoryID() == null){
                                        PatientTreatmentPlanHistory patientTreatmentPlanHistory =
                                                movePlanToHistory(treatmentPlanObjectItr.getId());

                                        treatmentPlanObjectItr.setHistoryID(patientTreatmentPlanHistory.getId().toString());
                                    }
                                    historyPlanObjects.add(treatmentPlanObjectItr);
                                }
                                else{
                                    historyPlanObjects.add(treatmentPlanObjectItr);
                                    treatmentPlanObjectItr.setHistoryID(historyID);
                                }
                                currentPlanObjects.add(treatmentPlanObjectItr);
                            }
                    );

            if(patientDentalDetailsMapping.getTreatmentPlanHistory() == null || patientDentalDetailsMapping.getTreatmentPlanHistory().isEmpty()){
                historyObject.setId(0);
            }
            else{
                historyObject.setId(patientDentalDetailsMapping.getTreatmentPlanHistory().size());
            }

            historyObject.setTreatmentPlans(historyPlanObjects);

            TreatmentPlanListObject currentPlans = patientDentalDetailsMapping.getTreatmentPlanLatest();
            if(historyID == null) currentPlanObjects.add(treatmentPlanObject);

            patientDentalDetailsMapping.setTreatmentPlanLatest(currentPlans);

            this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
        }
    }

    private PatientTreatmentPlanHistory movePlanToHistory(String planID) {
        PatientTreatmentPlan patientTreatmentPlan = getPlan(planID);
        PatientTreatmentPlanHistory patientTreatmentPlanHistory = this.patientTreatmentPlanHistoryMapper.mapper(patientTreatmentPlan);
        return this.patientTreatmentPlanHistoryRepo.save(patientTreatmentPlanHistory);
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
                .findByPatientIDAndTreatmentPlanID(patientID, treatmentPlanID);

        return patientTreatmentPlanDraft.isPresent() ? patientTreatmentPlanDraft.get() : new PatientTreatmentPlanDraft();
    }

    private PatientTreatmentPlanDraft getPatientTreatmentPlanDraftByClinicID(
            String patientID, String treatmentPlanID, String clinicID
    ) {
        Optional<PatientTreatmentPlanDraft> patientTreatmentPlanDraft = this.patientTreatmentPlanDraftRepo
                .findByPatientIDAndTreatmentPlanID(patientID, treatmentPlanID);

        return patientTreatmentPlanDraft.isPresent() ? patientTreatmentPlanDraft.get() : new PatientTreatmentPlanDraft();
    }

    private boolean checkPatientTreatmentPlan(PatientTreatmentPlan currentTreatmentPlan, PatientTreatmentPlan updatedTreatmentPlan) {
        return true;
    }

    private boolean compareTreatmentPlan(PatientTreatmentPlan currentTreatmentPlan, PatientTreatmentPlan updatedTreatmentPlan) {
        return true;
    }

    private PatientTreatmentPlan getPlan (String id){
        return this.patientTreatmentPlanRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Patient treatment plan", "id", id));
    }
}
