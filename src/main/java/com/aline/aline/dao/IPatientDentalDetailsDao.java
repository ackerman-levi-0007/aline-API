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

    PatientPreviousDentalHistory getPreviousDentalHistoryDetailsByPatientID(String patientID);
    PatientTreatmentGoal getPatientTreatmentGoalByPatientID(String patientID);
    PatientDentalDetail getPatientDentalDetailByPatientID(String patientID);
}
