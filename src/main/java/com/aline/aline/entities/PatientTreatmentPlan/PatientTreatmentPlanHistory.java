package com.aline.aline.entities.PatientTreatmentPlan;

import com.aline.aline.commonEntitiesObjects.Price;
import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.enums.MalocclusionTag;
import com.aline.aline.enums.TreatmentPlanCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PatientTreatmentPlanHistory")
public class PatientTreatmentPlanHistory {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;
    private String treatmentPlanID;
    private String label;
    private String patientID;
    private List<MalocclusionTag> malocclusionTag;
    private String caseAssessment;
    private String treatmentPlanSummary;
    private int upperSteps;
    private int lowerSteps;
    private int expectedDuration;
    private TreatmentPlanCategory treatmentPlanCategory;
    private Price price;
    private List<S3ImageObject> iprAndAttachmentReports;
    private List<String> treatmentSimulationsURL;
    private List<S3ImageObject> treatmentSimulationsAttachments;

    private Date originalCreatedOn;

    @CreatedDate
    @JsonIgnore
    private Date createdOn;
}
