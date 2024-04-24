package com.aline.aline.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

public class Patient {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
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

    @CreatedDate
    @JsonIgnore
    private Date createdOn;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedOn;
}
