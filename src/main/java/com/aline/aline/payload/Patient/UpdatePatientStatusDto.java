package com.aline.aline.payload.Patient;

import com.aline.aline.enums.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientStatusDto {
    private String patientID;

    private PatientStatus status;
}
