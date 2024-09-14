package com.aline.aline.services;

import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public interface IPatientDentalDetailsService {
    PatientPreviousDentalHistory createPreviousDentalHistoryDetails(int rebootID, PatientPreviousDentalHistory patientPreviousDentalHistoryDetails);
    PatientTreatmentGoal createPatientTreatmentGoal(int rebootID, PatientTreatmentGoal patientTreatmentGoal);
    PatientDentalDetail createPatientDentalDetail(int rebootID, PatientDentalDetail patientDentalDetail) throws BadRequestException;

    PatientPreviousDentalHistory updatePreviousDentalHistoryDetails(int rebootID, PatientPreviousDentalHistory patientPreviousDentalHistoryDetails);
    PatientTreatmentGoal updatePatientTreatmentGoal(int rebootID, PatientTreatmentGoal patientTreatmentGoal);
    PatientDentalDetail updatePatientDentalDetail(int rebootID, PatientDentalDetail patientDentalDetail) throws BadRequestException;

    Object getPreviousDentalHistoryDetailsByPatientID(String patientID, int rebootID);
    Object getPatientTreatmentGoalByPatientID(String patientID, int rebootID);
    Object getPatientDentalDetailByPatientID(String patientID, int rebootID);
}
