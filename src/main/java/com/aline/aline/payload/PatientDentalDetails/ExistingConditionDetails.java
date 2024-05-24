package com.aline.aline.payload.PatientDentalDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExistingConditionDetails {
    private boolean flag;
    private String  details;
}
