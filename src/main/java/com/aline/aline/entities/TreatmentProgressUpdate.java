package com.aline.aline.entities;

import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.enums.AlignerTracking;
import com.aline.aline.enums.VisitType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "TreatmentProgressUpdate")
public class TreatmentProgressUpdate {

    @JsonIgnore
    @MongoId(value = FieldType.OBJECT_ID)
    private ObjectId id;

    private int slug;
    private String patientID;
    private String progress;
    private VisitType visitType;
    private AlignerTracking alignerTracking;
    @JsonIgnore
    private boolean clickable = false;
    private String notes;
    private List<S3ImageObject> photos;

    @CreatedDate
    private Date createdOn;

    @LastModifiedDate
    private Date updatedOn;
}
