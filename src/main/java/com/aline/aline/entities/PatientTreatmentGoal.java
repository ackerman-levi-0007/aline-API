package com.aline.aline.entities;

import com.aline.aline.enums.ArchOptions;
import com.aline.aline.enums.DentalAttachmentsOptions;
import com.aline.aline.enums.IPROptions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "PatientTreatmentGoal")
public class PatientTreatmentGoal {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;

    private String patientID;

    private String correction;
    private ArchOptions arches;
    private IPROptions ipr;
    private String iprDetails;
    private DentalAttachmentsOptions attachments;
    private String attachmentsDetails;
    private String treatmentGoalOthers;
}
