package com.aline.aline.entities;

import com.aline.aline.enums.CommentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Comment")
public class Comment {

    @MongoId(value = FieldType.OBJECT_ID)
    private ObjectId id;

    private String userID;

    private String patientID;

    private String text;

    private CommentType commentType;

    @CreatedDate
    private DateTime commentedOn;
}
