package com.aline.aline.payload.TreatmentProgress;

import com.aline.aline.enums.VisitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TreatmentProgressListDto {
    private String id;
    private int slug;
    private String patientID;
    private VisitType visitType;
    private boolean clickable;
    private Date date;
}
