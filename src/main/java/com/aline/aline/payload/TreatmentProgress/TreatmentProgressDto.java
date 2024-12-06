package com.aline.aline.payload.TreatmentProgress;

import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.enums.AlignerTracking;
import com.aline.aline.enums.VisitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentProgressDto {
    private String id;
    private int slug;
    private String patientID;
    private String progress;
    private VisitType visitType;
    private AlignerTracking alignerTracking;
    private boolean clickable;
    private String notes;
    private List<String> photos;
    private Date createdOn;
}
