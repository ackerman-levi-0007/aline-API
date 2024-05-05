package com.aline.aline.payload.Patient;

import com.aline.aline.enums.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterPatientDto {
    private Date fromDateOfScan;
    private Date toDateOfScan;
    private List<String> patientID;
    private String name;
    private String gender;
    private List<String> clinicID;
    private List<String> doctorID;
    private PatientStatus status;
    private int fromAge =  0;
    private int toAge   =  0;
    private String nationality;
}
