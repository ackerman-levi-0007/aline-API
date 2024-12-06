package com.aline.aline.payload.TreatmentProgress;

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
    private String progress;
    private boolean clickable;
    private Date createdOn;
}
