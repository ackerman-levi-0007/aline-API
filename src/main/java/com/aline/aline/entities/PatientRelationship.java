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
@Document(collection = "PatientRelationship")
public class PatientRelationship {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;

    private String clinicDoctorID;

    private String patientID;

    private boolean status;
}
