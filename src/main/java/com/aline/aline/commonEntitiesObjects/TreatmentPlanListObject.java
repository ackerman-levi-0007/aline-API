package com.aline.aline.commonEntitiesObjects;

import com.aline.aline.enums.TreatmentPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPlanListObject {
    private int id;
    private List<TreatmentPlanObject> treatmentPlans;
    private TreatmentPlanStatus treatmentPlanStatus;
}
