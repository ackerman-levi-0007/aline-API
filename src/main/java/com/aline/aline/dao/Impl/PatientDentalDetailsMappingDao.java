package com.aline.aline.dao.Impl;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanListObject;
import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientDentalDetailsMapping.Reboot;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import com.aline.aline.repositories.PatientDentalDetailsMappingRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PatientDentalDetailsMappingDao implements IPatientDentalDetailsMappingDao {

    private final PatientDentalDetailsMappingRepo patientDentalDetailsMappingRepo;
    private final ModelMapper modelMapper;

    @Override
    public void createPatientDentalDetailsMapping(String clinicID, String doctorID, String patientID) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = new PatientDentalDetailsMapping();
        patientDentalDetailsMapping.setClinicID(clinicID);
        patientDentalDetailsMapping.setDoctorID(doctorID);
        patientDentalDetailsMapping.setPatientID(patientID);
        patientDentalDetailsMapping.setRebootID(0); // initial reboot

        patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public PatientDentalDetailsMapping getPatientDentalDetailsMapping(String patientID, int rebootID) {
        return getPatientDentalDetailsMappingForRebootID(patientID, rebootID);
    }

    @Override
    public void updatePatientPhotoScansID(String patientID, String photoScansID, int rebootID) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);
        patientDentalDetailsMapping.setPhotoScansId(photoScansID);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void updatePatientPreviousDentalHistoryDetailsID(
            String patientID,
            String previousDentalHistoryDetailsID,
            int rebootID
    ) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);
        patientDentalDetailsMapping.setPreviousDentalHistoryId(previousDentalHistoryDetailsID);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void updatePatientTreatmentGoalID(
            String patientID,
            String patientTreatmentGoalID,
            int rebootID
    ) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);
        patientDentalDetailsMapping.setTreatmentGoalId(patientTreatmentGoalID);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void addPatientTreatmentPlanID(
            String patientID,
            TreatmentPlanObject treatmentPlanObject,
            int rebootID,
            String draftID) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);

        TreatmentPlanListObject draftObject = patientDentalDetailsMapping.getTreatmentPlanDraft();
        List<TreatmentPlanObject> draftsList = draftObject.getTreatmentPlans();

        Optional<TreatmentPlanObject> draft = draftsList.stream().filter(x -> x.getId().equals(draftID)).findFirst();

        if(draft.isPresent()){
            treatmentPlanObject.setDisplayOrder(draft.get().getDisplayOrder());
            draft.get().setStatus(TreatmentPlanStatus.shared);
        }

        List<TreatmentPlanObject> sortedDraftsList = draftsList.stream()
                .sorted(Comparator.comparingInt(TreatmentPlanObject::getDisplayOrder))
                .collect(Collectors.toList());

        draftObject.setTreatmentPlans(sortedDraftsList);
        patientDentalDetailsMapping.setTreatmentPlanDraft(draftObject);

        TreatmentPlanListObject latestTreatmentPlan = patientDentalDetailsMapping.getTreatmentPlanLatest();

        if(latestTreatmentPlan == null){
            latestTreatmentPlan = new TreatmentPlanListObject();
            latestTreatmentPlan.setId(0);
        }

        List<TreatmentPlanObject> treatmentPlanObjects = latestTreatmentPlan.getTreatmentPlans();

        if( treatmentPlanObjects == null || treatmentPlanObjects.isEmpty()) treatmentPlanObjects = new ArrayList<>();

        treatmentPlanObjects.add(treatmentPlanObject);

        latestTreatmentPlan.setTreatmentPlans(treatmentPlanObjects);

        patientDentalDetailsMapping.setTreatmentPlanLatest(latestTreatmentPlan);

        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void addUnsavedDraftTreatmentPlanID(
            String patientID,
            TreatmentPlanObject treatmentPlanObject,
            int rebootID
    ) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);

        TreatmentPlanListObject draftTreatmentPlan = patientDentalDetailsMapping.getTreatmentPlanDraft();

        if(draftTreatmentPlan == null){
            draftTreatmentPlan = new TreatmentPlanListObject();
            draftTreatmentPlan.setId(0);
        }

        List<TreatmentPlanObject> treatmentPlanObjects = draftTreatmentPlan.getTreatmentPlans();

        if( treatmentPlanObjects == null || treatmentPlanObjects.isEmpty()) treatmentPlanObjects = new ArrayList<>();
        treatmentPlanObjects.add(treatmentPlanObject);

        draftTreatmentPlan.setTreatmentPlans(treatmentPlanObjects);

        patientDentalDetailsMapping.setTreatmentPlanDraft(draftTreatmentPlan);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void addTreatmentPlanToHistory(String patientID, List<TreatmentPlanObject> treatmentPlanObjects, int rebootID) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);

        List<TreatmentPlanListObject> historyTreatmentPlan = patientDentalDetailsMapping.getTreatmentPlanHistory();

        TreatmentPlanListObject treatmentPlanListObject = new TreatmentPlanListObject();

        if(historyTreatmentPlan == null || historyTreatmentPlan.isEmpty()){
            treatmentPlanListObject.setId(0);
            historyTreatmentPlan = new ArrayList<>();
        } else {
            treatmentPlanListObject.setId(historyTreatmentPlan.size());
        }

        treatmentPlanListObject.setTreatmentPlans(treatmentPlanObjects);

        historyTreatmentPlan.add(treatmentPlanListObject);

        patientDentalDetailsMapping.setTreatmentPlanHistory(historyTreatmentPlan);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void moveTreatmentPlanToHistory(String patientID, int rebootID) {

    }

    @Override
    public List<Integer> getAllRebootIds(String patientID) {
        List<PatientDentalDetailsMapping> patientDentalDetailsMappings = getPatientDentalDetailsMapping(patientID);
        return patientDentalDetailsMappings.stream().map(PatientDentalDetailsMapping::getRebootID).toList();
    }

    @Override
    public PatientTreatmentPlanMapping getPlanMapping(
            String patientID,
            int rebootID
    ) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);
        return this.modelMapper.map(patientDentalDetailsMapping, PatientTreatmentPlanMapping.class);
    }

    @Override
    public Reboot getReboot(String patientID) {
        List<PatientDentalDetailsMapping> patientDentalDetailsMappings = getPatientDentalDetailsMapping(patientID);

        Reboot reboot = new Reboot();
        reboot.setTotalReboots(patientDentalDetailsMappings.size());
        reboot.setLatestReboot(patientDentalDetailsMappings.size());

        return reboot;
    }

    @Override
    public void approvePlan(String patientID, int rebootID, String planID) {
        changePlanStatus(patientID, rebootID, planID, TreatmentPlanStatus.confirmed);
    }

    @Override
    public void planRequestModification(String patientID, int rebootID, String planID) {
        changePlanStatus(patientID, rebootID, planID, TreatmentPlanStatus.requestForModification);
    }

    @Override
    public void updateHistoryPlanMapping(
            String patientID,
            int rebootID,
            TreatmentPlanObject treatmentPlanObject,
            String treatmentPlanID,
            String draftID
    ) {
        PatientDentalDetailsMapping patientDentalDetailsMapping =
                getPatientDentalDetailsMappingForRebootID(patientID, rebootID);

        TreatmentPlanListObject draftObject = patientDentalDetailsMapping.getTreatmentPlanDraft();
        List<TreatmentPlanObject> draftsList = draftObject.getTreatmentPlans();

        Optional<TreatmentPlanObject> draft = draftsList.stream().filter(x -> x.getId().equals(draftID)).findFirst();

        if(draft.isPresent()){
            treatmentPlanObject.setDisplayOrder(draft.get().getDisplayOrder());
            draft.get().setStatus(TreatmentPlanStatus.shared);
        }

        List<TreatmentPlanObject> sortedDraftsList = draftsList.stream()
                .sorted(Comparator.comparingInt(TreatmentPlanObject::getDisplayOrder))
                .collect(Collectors.toList());

        draftObject.setTreatmentPlans(sortedDraftsList);
        patientDentalDetailsMapping.setTreatmentPlanDraft(draftObject);

        TreatmentPlanListObject latestObject = patientDentalDetailsMapping.getTreatmentPlanLatest();
        List<TreatmentPlanObject> latestPlanList = latestObject.getTreatmentPlans();

        Optional<TreatmentPlanObject> latestPlan = latestPlanList.stream().filter(x -> x.getId().equals(treatmentPlanID)).findFirst();

        if(latestPlan.isPresent()){
            latestPlan.get().setStatus(TreatmentPlanStatus.shared);

            for(int i=0 ; i<latestPlanList.size() ; i++)
                if (latestPlanList.get(i).getId().equals(treatmentPlanID)) {
                    latestPlanList.set(i, latestPlan.get());
                    break;
                }
        }

        List<TreatmentPlanObject> sortedLatestPlan = latestPlanList.stream()
                .sorted(Comparator.comparingInt(TreatmentPlanObject::getDisplayOrder))
                .collect(Collectors.toList());

        latestObject.setTreatmentPlans(sortedLatestPlan);
        patientDentalDetailsMapping.setTreatmentPlanLatest(latestObject);

        List<TreatmentPlanListObject> historyPlanObject = patientDentalDetailsMapping.getTreatmentPlanHistory();

        if(historyPlanObject == null || historyPlanObject.isEmpty()){

            historyPlanObject = new ArrayList<>();

            List<TreatmentPlanObject> treatmentPlans = new ArrayList<>();
            treatmentPlans.add(treatmentPlanObject);

              TreatmentPlanListObject historyObject = new TreatmentPlanListObject();
              historyObject.setId(0);
              historyObject.setTreatmentPlans(treatmentPlans);
              historyObject.setTreatmentPlanStatus(TreatmentPlanStatus.history);

              historyPlanObject.add(historyObject);
        }
        else{

            int objFoundIdx = -1;

            for(int i=0 ; i<historyPlanObject.size() ; i++){
                boolean historyObjFound = false;
                for(int j=0 ; j<historyPlanObject.get(i).getTreatmentPlans().size() ; j++){
                    if(historyPlanObject.get(i).getTreatmentPlans().get(j).getDisplayOrder()
                            == treatmentPlanObject.getDisplayOrder()){
                        historyObjFound = true;
                        break;
                    }
                }

                if(!historyObjFound){
                    objFoundIdx = i;
                    break;
                }
            }

            if(objFoundIdx == -1){
                List<TreatmentPlanObject> treatmentPlans = new ArrayList<>();
                treatmentPlans.add(treatmentPlanObject);

                TreatmentPlanListObject newHistoryRevision = new TreatmentPlanListObject();
                newHistoryRevision.setId(historyPlanObject.size());
                newHistoryRevision.setTreatmentPlans(treatmentPlans);
                newHistoryRevision.setTreatmentPlanStatus(TreatmentPlanStatus.history);

                historyPlanObject.add(newHistoryRevision);
            } else {
                TreatmentPlanListObject historyPlanFoundObj = historyPlanObject.get(objFoundIdx);
                historyPlanFoundObj.getTreatmentPlans().add(treatmentPlanObject);

                List<TreatmentPlanObject> sortedHistoryList = historyPlanFoundObj.getTreatmentPlans().stream()
                        .sorted(Comparator.comparingInt(TreatmentPlanObject::getDisplayOrder))
                        .collect(Collectors.toList());

                historyPlanFoundObj.setTreatmentPlans(sortedHistoryList);
                historyPlanObject.set(objFoundIdx, historyPlanFoundObj);
            }
        }
        patientDentalDetailsMapping.setTreatmentPlanHistory(historyPlanObject);

        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    /*****************************************************************************************
                                            Helpers
     *****************************************************************************************/
    private List<PatientDentalDetailsMapping> getPatientDentalDetailsMapping(String patientID){
        return this.patientDentalDetailsMappingRepo.findByPatientID(patientID)
                .orElseThrow(() -> new ResourceNotFoundException("Patient dental details mapping", "patientID", patientID)
                );
    }

    private PatientDentalDetailsMapping getPatientDentalDetailsMappingForRebootID(String patientID, int rebootID){
        return this.patientDentalDetailsMappingRepo.findByPatientIDAndRebootID(patientID, rebootID)
                .orElseThrow(() -> new ResourceNotFoundException("Patient dental details mapping", "patientID", patientID)
                );
    }

    private void changePlanStatus(String patientID, int rebootID, String planID, TreatmentPlanStatus treatmentPlanStatus){
        PatientDentalDetailsMapping patientDentalDetailsMapping =
                getPatientDentalDetailsMappingForRebootID(patientID, rebootID);

        TreatmentPlanListObject treatmentPlanListLatest = patientDentalDetailsMapping.getTreatmentPlanLatest();

        List<TreatmentPlanObject> treatmentPlanObjects =
                treatmentPlanListLatest.getTreatmentPlans().stream().peek(
                        x -> {
                            if(x.getId().equals(planID)) x.setStatus(treatmentPlanStatus);
                        }
                ).toList();

        treatmentPlanListLatest.setTreatmentPlans(treatmentPlanObjects);
        treatmentPlanListLatest.setTreatmentPlanStatus(treatmentPlanStatus);

        patientDentalDetailsMapping.setTreatmentPlanLatest(treatmentPlanListLatest);

        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }
}
