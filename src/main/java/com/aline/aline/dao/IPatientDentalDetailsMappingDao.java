package com.aline.aline.dao;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientDentalDetailsMappingDao {
    void createPatientDentalDetailsMapping(String clinicID, String doctorID, String patientID);
    PatientDentalDetailsMapping getPatientDentalDetailsMappingByPatientID(String patientID);
    void updatePatientPhotoScansIDForPatientID(String patientID, String id);
    void updatePatientPreviousDentalHistoryDetailsIDForPatientID(String patientID, String id);
    void updatePatientTreatmentGoalIDForPatientID(String patientID, String id);
    void updatePatientDentalDetailsIDForPatientID(String patientID, String previousDentalHistoryID, String patientTreatmentGoalID);
    void addPatientTreatmentPlanIDForPatientID(String patientID, TreatmentPlanObject treatmentPlanObject);
    void addUnsavedDraftTreatmentPlanIDForPatientID(String patientID, TreatmentPlanObject treatmentPlanObject);
}
