package com.aline.aline.services;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPatientDentalDetailsMappingService {
    List<Integer> getAllRebootIds(String patientID);
    PatientTreatmentPlanMapping getPlanMapping(String patientID, int rebootID);
    void addPatientTreatmentPlanID(String patientID, TreatmentPlanObject treatmentPlanObject, int rebootID);
    void addUnsavedDraftTreatmentPlanID(String patientID, TreatmentPlanObject treatmentPlanObject, int rebootID);
    void addTreatmentPlanToHistory(String patientID, List<TreatmentPlanObject> treatmentPlanObjects, int rebootID);
    void moveTreatmentPlanToHistory(String patientID, int rebootID);

}
