package com.aline.aline.dao;

import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface IDentalDetailsDao {
    PatientPreviousDentalHistory createPreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails, int rebootID);
    PatientTreatmentGoal createPatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal, int rebootID);
    PatientDentalDetail createPatientDentalDetail(PatientDentalDetail patientDentalDetail, int rebootID);

    PatientPreviousDentalHistory updatePreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails, int rebootID);
    PatientTreatmentGoal updatePatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal, int rebootID);
    PatientDentalDetail updatePatientDentalDetail(PatientDentalDetail patientDentalDetail, int rebootID);

    void deletePreviousDentalHistoryDetailsByPatientID(String patientID);
    void deletePatientTreatmentGoalByPatientID(String patientID);
    void deletePatientDentalDetailByPatientID(String patientID);

    PatientDentalDetail getPatientDentalDetail(String previousDentalHistoryId, String treatmentGoalId);
    PatientPreviousDentalHistory getPreviousDentalHistoryDetails(String previousDentalHistoryId);
    PatientTreatmentGoal getPatientTreatmentGoal(String treatmentGoalId);
}
