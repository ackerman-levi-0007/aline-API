package com.aline.aline.payload.PatientDentalDetails;

import com.aline.aline.entities.PatientPreviousDentalHistory;
import com.aline.aline.entities.PatientTreatmentGoal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientDentalDetail {
    private PatientPreviousDentalHistory patientPreviousDentalHistoryDetails;
    private PatientTreatmentGoal patientTreatmentGoal;
}
