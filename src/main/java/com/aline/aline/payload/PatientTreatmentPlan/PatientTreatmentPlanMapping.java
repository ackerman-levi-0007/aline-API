package com.aline.aline.payload.PatientTreatmentPlan;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanHistoryObject;
import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientTreatmentPlanMapping {
    private TreatmentPlanObjectStatus treatmentPlanLatest;
    private List<TreatmentPlanHistoryObject> treatmentPlanHistory;
    private TreatmentPlanObjectStatus treatmentPlanDraft;
}
