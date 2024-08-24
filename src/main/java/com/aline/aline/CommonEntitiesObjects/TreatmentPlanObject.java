package com.aline.aline.CommonEntitiesObjects;

import com.aline.aline.enums.TreatmentPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPlanObject {
    private String id;
    private TreatmentPlanStatus status;
}
