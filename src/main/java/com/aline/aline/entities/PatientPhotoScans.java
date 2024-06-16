package com.aline.aline.entities;

import com.aline.aline.CommonEntitiesObjects.S3ImageObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "PatientPhotoScans")
public class PatientPhotoScans {
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;

    private String patientID;

    private List<S3ImageObject> profilePhoto;

    private List<S3ImageObject> extFront;

    private List<S3ImageObject> extSide;

    private List<S3ImageObject> extOblique;

    private List<S3ImageObject> intFront;

    private List<S3ImageObject> intSideLeft;

    private List<S3ImageObject> intFrontRight;

    private List<S3ImageObject> intMaxilla;

    private List<S3ImageObject> intMandible;

    private List<S3ImageObject> opg;

    private List<S3ImageObject> cep;

    private List<S3ImageObject> scans;
}

