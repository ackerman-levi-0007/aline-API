package com.aline.aline.entities.PatientTreatmentPlan;

import com.aline.aline.CommonEntitiesObjects.Price;
import com.aline.aline.enums.MalocclusionTag;
import com.aline.aline.enums.TreatmentPlanCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "PatientTreatmentPlanDraft")
public class PatientTreatmentPlanDraft {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;
    private String treatmentPlanID;
    private String doctorID;
    private String clinicID;
    private String patientID;
    private List<MalocclusionTag> malocclusionTag;
    private String caseAssessment;
    private String treatmentPlanSummary;
    private int upperSteps;
    private int lowerSteps;
    private int expectedDuration;
    private TreatmentPlanCategory treatmentPlanCategory;
    private Price price;
    private List<String> iprAndAttachmentReports;
    private List<String> treatmentSimulationsURL;
    private List<String> treatmentSimulationsAttachments;

    @CreatedDate
    @JsonIgnore
    private Date createdOn;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedOn;
}
