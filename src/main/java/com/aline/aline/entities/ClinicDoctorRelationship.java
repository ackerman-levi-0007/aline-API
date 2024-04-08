package com.aline.aline.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "ClinicDoctorRelationship")
public class ClinicDoctorRelationship {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;

    private String clinicID;

    private String doctorID;

    private boolean status;

    public ClinicDoctorRelationship (String clinicID, String doctorID, boolean status){
        this.setDoctorID(doctorID);
        this.setClinicID(clinicID);
        this.setStatus(status);
    }
}
