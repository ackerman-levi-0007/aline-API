package com.aline.aline.dao;

import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientDentalDetailsDao {
    PatientPreviousDentalHistory createPreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails);
    PatientTreatmentGoal createPatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal);
    PatientDentalDetail createPatientDentalDetail(PatientDentalDetail patientDentalDetail);

    PatientPreviousDentalHistory updatePreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails);
    PatientTreatmentGoal updatePatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal);
    PatientDentalDetail updatePatientDentalDetail(PatientDentalDetail patientDentalDetail);

    PatientDentalDetail getPatientDentalDetail(String previousDentalHistoryId, String treatmentGoalId);

    void deletePreviousDentalHistoryDetailsByPatientID(String patientID);
    void deletePatientTreatmentGoalByPatientID(String patientID);
    void deletePatientDentalDetailByPatientID(String patientID);

    PatientPreviousDentalHistory getPreviousDentalHistoryDetails(String previousDentalHistoryId);
    PatientTreatmentGoal getPatientTreatmentGoal(String treatmentGoalId);
}
