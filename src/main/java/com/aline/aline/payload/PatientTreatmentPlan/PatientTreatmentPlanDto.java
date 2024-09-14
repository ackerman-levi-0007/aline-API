package com.aline.aline.payload.PatientTreatmentPlan;

import com.aline.aline.commonEntitiesObjects.Price;
import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.enums.MalocclusionTag;
import com.aline.aline.enums.TreatmentPlanCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientTreatmentPlanDto {
    private String id;
    private String label;
    private String patientID;
    private String treatmentPlanID;
    private List<MalocclusionTag> malocclusionTag;
    private String caseAssessment;
    private String treatmentPlanSummary;
    private int upperSteps;
    private int lowerSteps;
    private int expectedDuration;
    private TreatmentPlanCategory treatmentPlanCategory;
    private Price price;
    private List<S3ImageObject> iprAndAttachmentReports;
    private List<S3ImageObject> treatmentSimulationsURL;
    private List<S3ImageObject> treatmentSimulationsAttachments;
}
