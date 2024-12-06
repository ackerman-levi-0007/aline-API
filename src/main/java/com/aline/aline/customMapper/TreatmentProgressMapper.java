package com.aline.aline.customMapper;

import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.entities.TreatmentProgressUpdate;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressDto;

public class TreatmentProgressMapper {
    public static TreatmentProgressDto mapper(TreatmentProgressUpdate treatmentProgressUpdate){
        TreatmentProgressDto treatmentProgressDto = new TreatmentProgressDto();

        treatmentProgressDto.setId(treatmentProgressUpdate.getId().toString());
        treatmentProgressDto.setSlug(treatmentProgressUpdate.getSlug());
        treatmentProgressDto.setProgress(treatmentProgressUpdate.getProgress());
        treatmentProgressDto.setVisitType(treatmentProgressUpdate.getVisitType());
        treatmentProgressDto.setAlignerTracking(treatmentProgressUpdate.getAlignerTracking());
        treatmentProgressDto.setClickable(treatmentProgressUpdate.isClickable());
        treatmentProgressDto.setNotes(treatmentProgressUpdate.getNotes());
        treatmentProgressDto.setPhotos(treatmentProgressUpdate.getPhotos().stream().map(S3ImageObject::getURL).toList());
        treatmentProgressDto.setCreatedOn(treatmentProgressUpdate.getCreatedOn());

        return treatmentProgressDto;
    }
}
