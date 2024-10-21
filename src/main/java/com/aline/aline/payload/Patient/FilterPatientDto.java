package com.aline.aline.payload.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterPatientDto {
    private Date fromDateOfScan;
    private Date toDateOfScan;
    private List<String> patientID  =   new ArrayList<>();
    private String name;
    private String gender;
    private List<String> clinicID   =   new ArrayList<>();
    private List<String> doctorID   =   new ArrayList<>();
    private String status;
    private int fromAge =  0;
    private int toAge   =  0;
    private String nationality;
}
