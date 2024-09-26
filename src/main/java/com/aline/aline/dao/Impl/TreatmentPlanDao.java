package com.aline.aline.dao.Impl;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.customMapper.DeepClone;
import com.aline.aline.customMapper.PatientTreatmentPlanDraftMapper;
import com.aline.aline.customMapper.PatientTreatmentPlanHistoryMapper;
import com.aline.aline.customMapper.PatientTreatmentPlanMapper;
import com.aline.aline.dao.ITreatmentPlanDao;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanDraftRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanHistoryRepo;
import com.aline.aline.repositories.PatientTreatmentPlan.PatientTreatmentPlanRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TreatmentPlanDao implements ITreatmentPlanDao {

    private final PatientTreatmentPlanRepo patientTreatmentPlanRepo;
    private final PatientTreatmentPlanDraftRepo patientTreatmentPlanDraftRepo;
    private final PatientTreatmentPlanHistoryRepo patientTreatmentPlanHistoryRepo;
    private final PatientTreatmentPlanDraftMapper patientTreatmentPlanDraftMapper;
    private final PatientTreatmentPlanHistoryMapper patientTreatmentPlanHistoryMapper;
    private final PatientTreatmentPlanMapper patientTreatmentPlanMapper;
    private final DentalDetailsMappingDao patientDentalDetailsMappingDao;


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

        if(patientTreatmentPlanDraft.getTreatmentPlanID() == null){
            PatientTreatmentPlan savedPlan = createPlan(patientTreatmentPlanDraft);

            TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
            treatmentPlanObject.setId(savedPlan.getId().toString());
            treatmentPlanObject.setLabel(savedPlan.getLabel());
            treatmentPlanObject.setDraftID(draftID);
            treatmentPlanObject.setStatus(TreatmentPlanStatus.shared);

            this.patientDentalDetailsMappingDao.addPatientTreatmentPlanID(savedPlan.getPatientID(), treatmentPlanObject ,rebootID, draftID);
        }
        else{
            PatientTreatmentPlanHistory historyPlan = updatePlan(patientTreatmentPlanDraft);

            TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
            treatmentPlanObject.setId(historyPlan.getId().toString());
            treatmentPlanObject.setLabel(historyPlan.getLabel());
            treatmentPlanObject.setStatus(TreatmentPlanStatus.shared);

            this.patientDentalDetailsMappingDao.updateHistoryPlanMapping(patientID, rebootID, treatmentPlanObject, historyPlan.getTreatmentPlanID(), draftID);
        }
    }

    private PatientTreatmentPlanHistory updatePlan(PatientTreatmentPlanDraft patientTreatmentPlanDraft) {
        PatientTreatmentPlan patientTreatmentPlan = getPlan(patientTreatmentPlanDraft.getTreatmentPlanID());

        PatientTreatmentPlan historyPlan = DeepClone.deepClone(patientTreatmentPlan);

        PatientTreatmentPlan updatedPlan = this.patientTreatmentPlanMapper.PlanDraftSetter(patientTreatmentPlan, patientTreatmentPlanDraft);

        this.patientTreatmentPlanRepo.save(updatedPlan);
        PatientTreatmentPlanHistory patientTreatmentPlanHistory = this.patientTreatmentPlanHistoryMapper
                .mapper(historyPlan);

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
