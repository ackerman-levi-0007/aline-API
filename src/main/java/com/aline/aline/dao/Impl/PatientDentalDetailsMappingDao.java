package com.aline.aline.dao.Impl;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanListObject;
import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import com.aline.aline.repositories.PatientDentalDetailsMappingRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

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

        List<TreatmentPlanObject> treatmentPlanObjects = latestTreatmentPlan.getTreatmentPlans();
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

        List<TreatmentPlanObject> treatmentPlanObjects = draftTreatmentPlan.getTreatmentPlans();
        treatmentPlanObjects.add(treatmentPlanObject);

        draftTreatmentPlan.setTreatmentPlans(treatmentPlanObjects);

        patientDentalDetailsMapping.setTreatmentPlanDraft(draftTreatmentPlan);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public List<Integer> getAllRebootIds(String patientID) {
        return List.of();
    }

    @Override
    public PatientTreatmentPlanMapping getPlanMapping(
            String patientID,
            int rebootID
    ) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMappingForRebootID(patientID, rebootID);
        return this.modelMapper.map(patientDentalDetailsMapping, PatientTreatmentPlanMapping.class);
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
}
