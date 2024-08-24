package com.aline.aline.CommonEntitiesObjects;

import com.aline.aline.enums.TreatmentPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPlanHistoryObject {
    private int id;
    private List<TreatmentPlanObject> treatmentPlanHistory;
    private TreatmentPlanStatus treatmentPlanStatus;
}
