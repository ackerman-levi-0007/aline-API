package com.aline.aline.payload.TreatmentProgress;

import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.enums.AlignerTracking;
import com.aline.aline.enums.VisitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TreatmentProgressUpdateDto {
    private String id;
    private int slug;
    private String patientID;
    private Date date;
    private VisitType visitType;
    private AlignerTracking alignerTracking;
    private String scanURL;
    private String notes;
    private List<S3ImageObject> photos;
}
