package com.aline.aline.utilities;

import com.aline.aline.commonEntitiesObjects.S3ImageObject;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommonUtils {
    public static boolean isNullOrEmpty(String str){
        return str == null || str.isEmpty();
    }

    public static List<S3ImageObject> getUpdateListForS3Images(List<S3ImageObject> oldS3ImageObjects,
                                                               List<S3ImageObject> updatedS3ImageObjects
    ){
        Set<String> urls = new HashSet<>();
        updatedS3ImageObjects.forEach(x -> {
            if(x.getKey() == null) urls.add(x.getURL());
        });

        updatedS3ImageObjects.replaceAll(x -> urls.contains(x.getURL()) ?
                getS3ObjectFromListByURL(oldS3ImageObjects, x.getURL()) : x);

        return updatedS3ImageObjects;
    }

    public static S3ImageObject getS3ObjectFromListByURL(List<S3ImageObject> s3ImageObjectList, String url){
        for(S3ImageObject s3ImageObject : s3ImageObjectList){
            if(Objects.equals(s3ImageObject.getURL(), url)) return s3ImageObject;
        }
        return null;
    }
}
