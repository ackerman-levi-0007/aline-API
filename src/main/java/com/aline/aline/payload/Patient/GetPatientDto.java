package com.aline.aline.payload.Patient;

import com.aline.aline.enums.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPatientDto {

    private String id;

    private String name;

    private String clinicID;

    private String doctorID;

    private PatientStatus status;

    private Date dateOfScan;

    private String gender;

    private int age;

    private String nationality;

    private Date createdOn;

    private Date updatedOn;
}
