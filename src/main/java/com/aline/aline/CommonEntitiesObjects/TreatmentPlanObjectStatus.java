package com.aline.aline.CommonEntitiesObjects;

import com.aline.aline.enums.TreatmentPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPlanObjectStatus {
    private List<TreatmentPlanObject> treatmentPlanList;
    private TreatmentPlanStatus treatmentPlanStatus;
}
