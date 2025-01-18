package com.aline.aline.entities;

import com.aline.aline.enums.PatientStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Patient")
public class Patient {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private String id;

    private String filePatientID;
    private String labPatientID;

    @NonNull
    private String name;

    @NonNull
    private String clinicID;

    @NonNull
    private String doctorID;

    private PatientStatus status;

    private Date dateOfScan;

    private String gender;

    private int age;

    private String nationality;

    @JsonIgnore
    private String profilePhoto;

    @CreatedDate
    @JsonIgnore
    private Date createdOn;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedOn;
}
