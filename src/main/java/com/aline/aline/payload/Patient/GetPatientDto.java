package com.aline.aline.payload.Patient;

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

    private String status;

    private Date dateOfScan;

    private String gender;

    private int age;

    private String nationality;

    private String chiefComplaint;

    private Date createdOn;

    private Date updatedOn;
}
