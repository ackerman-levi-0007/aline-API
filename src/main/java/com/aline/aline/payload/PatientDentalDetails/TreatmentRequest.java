package com.aline.aline.payload.PatientDentalDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreatmentRequest {
    private boolean flag;
    private String  details;
}
