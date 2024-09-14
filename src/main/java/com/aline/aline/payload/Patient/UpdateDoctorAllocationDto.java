package com.aline.aline.payload.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDoctorAllocationDto {
    private String patientID;
    private String oldDoctorID;
    private String newDoctorID;
}
