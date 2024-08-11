package com.aline.aline.entities;

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

    private String patientPreviousDentalHistoryId;
    private String patientTreatmentGoalId;
    private String patientPhotoScansId;
    private List<String> patientTreatmentPlanId;
    private List<String> patientTreatmentPlanDraftId; //Drafts that are created but not committed in patient treatment plan
}
