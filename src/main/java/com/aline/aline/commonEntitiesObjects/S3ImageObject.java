package com.aline.aline.commonEntitiesObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class S3ImageObject {
    private String Key;
    private String URL;
}
