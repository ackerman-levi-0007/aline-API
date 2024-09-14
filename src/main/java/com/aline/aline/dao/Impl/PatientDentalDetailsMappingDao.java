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

import java.util.ArrayList;
import java.util.List;

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
    public void updatePatientDentalDetailsID(
            String patientID,
            String previousDentalHistoryID,
            String patientTreatmentGoalID,
            int rebootID
    ) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);
        patientDentalDetailsMapping.setPreviousDentalHistoryId(previousDentalHistoryID);
        patientDentalDetailsMapping.setTreatmentGoalId(patientTreatmentGoalID);
    }

    @Override
    public void addPatientTreatmentPlanID(
            String patientID,
            TreatmentPlanObject treatmentPlanObject,
            int rebootID
    ) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);

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
