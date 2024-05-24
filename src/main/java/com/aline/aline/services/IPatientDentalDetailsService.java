package com.aline.aline.services;

import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import com.aline.aline.payload.PatientDentalDetails.PatientDentalDetail;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public interface IPatientDentalDetailsService {
    PatientPreviousDentalHistory createPreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails);
    PatientTreatmentGoal createPatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal);
    PatientDentalDetail createPatientDentalDetail(PatientDentalDetail patientDentalDetail) throws BadRequestException;

    PatientPreviousDentalHistory updatePreviousDentalHistoryDetails(PatientPreviousDentalHistory patientPreviousDentalHistoryDetails);
    PatientTreatmentGoal updatePatientTreatmentGoal(PatientTreatmentGoal patientTreatmentGoal);
    PatientDentalDetail updatePatientDentalDetail(PatientDentalDetail patientDentalDetail) throws BadRequestException;

    Object getPreviousDentalHistoryDetailsByPatientID(String patientID);
    Object getPatientTreatmentGoalByPatientID(String patientID);
    Object getPatientDentalDetailByPatientID(String patientID);
}
