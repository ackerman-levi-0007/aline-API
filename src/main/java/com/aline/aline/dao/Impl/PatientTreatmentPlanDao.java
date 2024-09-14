package com.aline.aline.dao.Impl;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanListObject;
import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.customMapper.PatientTreatmentPlanDraftMapper;
import com.aline.aline.customMapper.PatientTreatmentPlanHistoryMapper;
import com.aline.aline.customMapper.PatientTreatmentPlanMapper;
import com.aline.aline.dao.IPatientTreatmentPlanDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import com.aline.aline.repositories.PatientDentalDetailsMappingRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanDraftRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanHistoryRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanRepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
            List<TreatmentPlanObject> latestPlanObjects = patientDentalDetailsMapping.getTreatmentPlanLatest()
                    .getTreatmentPlans();

            List<TreatmentPlanObject> historyPlanObjects = new ArrayList<>();


            for(int i=0 ; i<latestPlanObjects.size(); i++){
                TreatmentPlanObject itr = latestPlanObjects.get(i);

                // Use equals() to compare object references such as id and getId()
                if (!id.equals(itr.getId())) {
                    if (itr.getHistoryID() == null) {
                        // Move the plan to history and update the historyID
                        PatientTreatmentPlanHistory patientTreatmentPlanHistory =
                                movePlanToHistory(itr.getId());

                        // Set the historyID with the new history ID
                        itr.setHistoryID(patientTreatmentPlanHistory.getId().toString());
                    }
                } else {
                    // Set the historyID directly if id matches
                    itr.setHistoryID(historyID);
                }

                latestPlanObjects.set(i, itr);

                TreatmentPlanObject historyItr = new TreatmentPlanObject();
                historyItr.setId(itr.getHistoryID());
                historyItr.setStatus(itr.getStatus());

                historyPlanObjects.add(historyItr);
            }

            TreatmentPlanListObject historyObject = new TreatmentPlanListObject();
            if(patientDentalDetailsMapping.getTreatmentPlanHistory() == null || patientDentalDetailsMapping.getTreatmentPlanHistory().isEmpty()){
                historyObject.setId(0);
            }
            else{
                historyObject.setId(patientDentalDetailsMapping.getTreatmentPlanHistory().size());
            }

            historyObject.setTreatmentPlanStatus(TreatmentPlanStatus.history);
            historyObject.setTreatmentPlans(historyPlanObjects);

            List<TreatmentPlanListObject> historyPrvPlanObjects =
                    patientDentalDetailsMapping.getTreatmentPlanHistory() == null ? new ArrayList<>() : patientDentalDetailsMapping.getTreatmentPlanHistory();
            historyPrvPlanObjects.add(historyObject);
            patientDentalDetailsMapping.setTreatmentPlanHistory(historyPrvPlanObjects);

            TreatmentPlanListObject currentPlans = patientDentalDetailsMapping.getTreatmentPlanLatest();
            if(historyID == null) latestPlanObjects.add(treatmentPlanObject);

            currentPlans.setTreatmentPlans(latestPlanObjects);
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

    private PatientTreatmentPlanDraft getPatientTreatmentPlanDraft(String patientID, String id){
        return this.patientTreatmentPlanDraftRepo.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Draft","id", id)
                );
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
