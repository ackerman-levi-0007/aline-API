package com.aline.aline.payload.Patient;

import com.aline.aline.enums.PatientStatus;
import com.aline.aline.payload.PatientDentalDetailsMapping.Reboot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientListDto {
    private String id;
    private String filePatientID;
    private String labPatientID;
    private String profilePhoto;
    private String name;
    private String clinicID;
    private String doctorID;
    private PatientStatus status;
    private Date dateOfScan;
    private String gender;
    private int age;
    private String nationality;
    private Reboot reboot;
}
