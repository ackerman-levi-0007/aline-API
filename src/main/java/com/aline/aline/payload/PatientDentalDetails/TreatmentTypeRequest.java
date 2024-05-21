package com.aline.aline.payload.PatientDentalDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TreatmentTypeRequest {
    private String treatmentType;
    private String treatmentDetails;
}
