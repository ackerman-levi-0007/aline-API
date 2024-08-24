package com.aline.aline.dao.Impl;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObjectStatus;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.repositories.PatientDentalDetailsMappingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PatientDentalDetailsMappingDao implements IPatientDentalDetailsMappingDao {

    private final PatientDentalDetailsMappingRepo patientDentalDetailsMappingRepo;

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
    public PatientDentalDetailsMapping getPatientDentalDetailsMappingByPatientID(String patientID) {
        return getPatientDentalDetailsMapping(patientID);
    }

    @Override
    public void updatePatientPhotoScansIDForPatientID(String patientID, String id) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);
        patientDentalDetailsMapping.setPhotoScansId(id);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void updatePatientPreviousDentalHistoryDetailsIDForPatientID(String patientID, String id) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);
        patientDentalDetailsMapping.setPreviousDentalHistoryId(id);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void updatePatientTreatmentGoalIDForPatientID(String patientID, String id) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);
        patientDentalDetailsMapping.setTreatmentGoalId(id);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void updatePatientDentalDetailsIDForPatientID(String patientID, String previousDentalHistoryID, String patientTreatmentGoalID) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);
        patientDentalDetailsMapping.setPreviousDentalHistoryId(previousDentalHistoryID);
        patientDentalDetailsMapping.setTreatmentGoalId(patientTreatmentGoalID);
    }

    @Override
    public void addPatientTreatmentPlanIDForPatientID(String patientID, TreatmentPlanObject treatmentPlanObject) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);

        TreatmentPlanObjectStatus latestTreatmentPlan = patientDentalDetailsMapping.getTreatmentPlanLatest();

        List<TreatmentPlanObject> treatmentPlanObjects = latestTreatmentPlan.getTreatmentPlanList();
        treatmentPlanObjects.add(treatmentPlanObject);

        latestTreatmentPlan.setTreatmentPlanList(treatmentPlanObjects);

        patientDentalDetailsMapping.setTreatmentPlanLatest(latestTreatmentPlan);

        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void addUnsavedDraftTreatmentPlanIDForPatientID(String patientID, TreatmentPlanObject treatmentPlanObject) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);

        TreatmentPlanObjectStatus draftTreatmentPlan = patientDentalDetailsMapping.getTreatmentPlanDraft();

        List<TreatmentPlanObject> treatmentPlanObjects = draftTreatmentPlan.getTreatmentPlanList();
        treatmentPlanObjects.add(treatmentPlanObject);

        draftTreatmentPlan.setTreatmentPlanList(treatmentPlanObjects);

        patientDentalDetailsMapping.setTreatmentPlanDraft(draftTreatmentPlan);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    /*****************************************************************************************
                                            Helpers
     *****************************************************************************************/

    private PatientDentalDetailsMapping getPatientDentalDetailsMapping(String patientID){
        return this.patientDentalDetailsMappingRepo.findByPatientID(patientID)
                .orElseThrow(() -> new ResourceNotFoundException("Patient dental details mapping", "patientID", patientID)
                );
    }
}
