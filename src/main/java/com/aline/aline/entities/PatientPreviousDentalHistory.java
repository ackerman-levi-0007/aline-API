package com.aline.aline.entities;

import com.aline.aline.payload.PatientDentalDetails.ExistingConditionDetails;
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
@Document(collection = "PatientPreviousDentalHistory")
public class PatientPreviousDentalHistory {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;

    private String patientID;

    private String chiefComplaint;

    private ExistingConditionDetails crownsBridges;

    private ExistingConditionDetails implants;

    private ExistingConditionDetails veneers;

    private ExistingConditionDetails previousTreatment;

    private ExistingConditionDetails composites;

    private String historyOthers;
}
