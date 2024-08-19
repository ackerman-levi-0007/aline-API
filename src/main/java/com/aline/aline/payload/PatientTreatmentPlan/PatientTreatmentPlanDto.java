package com.aline.aline.payload.PatientTreatmentPlan;

import com.aline.aline.CommonEntitiesObjects.Price;
import com.aline.aline.enums.MalocclusionTag;
import com.aline.aline.enums.TreatmentPlanCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientTreatmentPlanDto {
    private String id;
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
    private Date createdOn;
    private Date updatedOn;
}
