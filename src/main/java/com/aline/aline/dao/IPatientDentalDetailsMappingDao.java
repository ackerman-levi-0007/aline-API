package com.aline.aline.dao;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.payload.PatientDentalDetailsMapping.Reboot;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientDentalDetailsMappingDao {
    void createPatientDentalDetailsMapping(String clinicID, String doctorID, String patientID);
    PatientDentalDetailsMapping getPatientDentalDetailsMapping(String patientID, int rebootID);
    void updatePatientPhotoScansID(String patientID, String photoScansID,  int rebootID);
    void updatePatientPreviousDentalHistoryDetailsID(String patientID, String id, int rebootID);
    void updatePatientTreatmentGoalID(String patientID, String patientTreatmentGoalID, int rebootID);
    void addPatientTreatmentPlanID(String patientID, TreatmentPlanObject treatmentPlanObject, int rebootID);
    void addUnsavedDraftTreatmentPlanID(String patientID, TreatmentPlanObject treatmentPlanObject, int rebootID);
    void addTreatmentPlanToHistory(String patientID, List<TreatmentPlanObject> treatmentPlanObjects, int rebootID);
    void moveTreatmentPlanToHistory(String patientID, int rebootID);
    List<Integer> getAllRebootIds(String patientID);
    PatientTreatmentPlanMapping getPlanMapping(String patientID, int rebootID);
    Reboot getReboot(String patientID);
    void approvePlan(String patientID, int rebootID, String planID);
    void planRequestModification(String patientID, int rebootID, String planID);
}
