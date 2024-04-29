package com.aline.aline.payload.Patient;

import com.aline.aline.enums.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientDto {
    private String id;

    private String name;

    private PatientStatus status;

    private Date dateOfScan;

    private String gender;

    private int age;

    private String nationality;

    private String chiefComplaint;
}
