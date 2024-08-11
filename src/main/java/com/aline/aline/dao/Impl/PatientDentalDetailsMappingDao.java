package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.enums.PatientDentalDetailsMappingField;
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

        patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public PatientDentalDetailsMapping getPatientDentalDetailsMappingByPatientID(String patientID) {
        return getPatientDentalDetailsMapping(patientID);
    }

    @Override
    public void updatePatientPhotoScansIDForPatientID(String patientID, String id) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);
        patientDentalDetailsMapping.setPatientPhotoScansId(id);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void updatePatientPreviousDentalHistoryDetailsIDForPatientID(String patientID, String id) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);
        patientDentalDetailsMapping.setPatientPreviousDentalHistoryId(id);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void updatePatientTreatmentGoalIDForPatientID(String patientID, String id) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);
        patientDentalDetailsMapping.setPatientTreatmentGoalId(id);
        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void updatePatientDentalDetailsIDForPatientID(String patientID, String previousDentalHistoryID, String patientTreatmentGoalID) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);
        patientDentalDetailsMapping.setPatientPreviousDentalHistoryId(previousDentalHistoryID);
        patientDentalDetailsMapping.setPatientTreatmentGoalId(patientTreatmentGoalID);
    }

    @Override
    public void addPatientTreatmentPlanIDForPatientID(String patientID, String id) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);

        List<String> patientTreatmentPlanID = patientDentalDetailsMapping.getPatientTreatmentPlanId();
        patientTreatmentPlanID.add(id);

        patientDentalDetailsMapping.setPatientTreatmentPlanId(
                patientTreatmentPlanID
        );

        this.patientDentalDetailsMappingRepo.save(patientDentalDetailsMapping);
    }

    @Override
    public void addUnsavedDraftTreatmentPlanIDForPatientID(String patientID, String id) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = getPatientDentalDetailsMapping(patientID);

        List<String> patientTreatmentPlanDraft = patientDentalDetailsMapping.getPatientTreatmentPlanDraftId();
        patientTreatmentPlanDraft.add(id);

        patientDentalDetailsMapping.setPatientTreatmentPlanDraftId(patientTreatmentPlanDraft);
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
