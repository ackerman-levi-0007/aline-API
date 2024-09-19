package com.aline.aline.commonEntitiesObjects;

import com.aline.aline.enums.TreatmentPlanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPlanObject {
    private String id;
    private String label;

    @JsonIgnore
    private int displayOrder;
    private TreatmentPlanStatus status;
}
