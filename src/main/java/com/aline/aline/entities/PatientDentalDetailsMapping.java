package com.aline.aline.entities;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanListObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PatientDentalDetailsMapping")
public class PatientDentalDetailsMapping {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;

    private String clinicID;
    private String doctorID;
    private String patientID;

    private int rebootID;
    private String previousDentalHistoryId;
    private String treatmentGoalId;
    private String photoScansId;
    private TreatmentPlanListObject treatmentPlanLatest;
    private List<TreatmentPlanListObject> treatmentPlanHistory;
    private TreatmentPlanListObject treatmentPlanDraft;
}
