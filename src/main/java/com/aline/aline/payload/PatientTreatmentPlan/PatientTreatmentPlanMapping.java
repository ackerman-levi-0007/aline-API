package com.aline.aline.payload.PatientTreatmentPlan;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanListObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientTreatmentPlanMapping {
    private TreatmentPlanListObject treatmentPlanLatest;
    private List<TreatmentPlanListObject> treatmentPlanHistory;
    private TreatmentPlanListObject treatmentPlanDraft;
}
