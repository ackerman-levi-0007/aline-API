package com.aline.aline.utilities;

import com.aline.aline.cache.ThreadLocalCache;
import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.entities.PatientDentalDetailsMapping;

import java.util.*;

public class CommonUtils {
    public static boolean isNullOrEmpty(String str){
        return str == null || str.isEmpty();
    }

    public static <T> T getFirstOrNull(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static List<S3ImageObject> getUpdateListForS3Images(List<S3ImageObject> oldS3ImageObjects,
                                                               List<S3ImageObject> updatedS3ImageObjects
    ){
        Set<String> urls = new HashSet<>();

        if (updatedS3ImageObjects != null && urls != null && oldS3ImageObjects != null) {
            updatedS3ImageObjects.forEach(x -> {
                if(x.getKey() == null) urls.add(x.getURL());
            });

            updatedS3ImageObjects.replaceAll(x -> {
                String url = (x != null) ? x.getURL() : null;
                if (url != null && urls.contains(url)) {
                    S3ImageObject replacement = getS3ObjectFromListByURL(oldS3ImageObjects, url);
                    return (replacement != null) ? replacement : x; // Keep the original if replacement is null
                }
                return x;
            });
        }

        return updatedS3ImageObjects;
    }

    public static PatientDentalDetailsMapping getPatientPlanMapping(){
        return (PatientDentalDetailsMapping) ThreadLocalCache.get("patientDentalDetailsMapping");
    }

    public static S3ImageObject getS3ObjectFromListByURL(List<S3ImageObject> s3ImageObjectList, String url){
        for(S3ImageObject s3ImageObject : s3ImageObjectList){
            if(Objects.equals(s3ImageObject.getURL(), url)) return s3ImageObject;
        }
        return null;
    }
}
