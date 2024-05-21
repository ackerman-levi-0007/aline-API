package com.aline.aline.entities;

import com.aline.aline.payload.PatientDentalDetails.TreatmentRequest;
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
@Document(collection = "PatientDentalDetails")
public class PatientDentalDetails {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;

    private String patientID;

    private String chiefComplaint;

    private TreatmentRequest crownsBridges;

    private TreatmentRequest implants;

    private TreatmentRequest veneers;

    private TreatmentRequest previousOrthodonticTreatment;

    private TreatmentRequest compositesBuildup;

    private String otherDetails;
}
