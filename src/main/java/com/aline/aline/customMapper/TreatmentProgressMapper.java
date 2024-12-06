package com.aline.aline.customMapper;

import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.entities.TreatmentProgressUpdate;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressDto;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressListDto;

public class TreatmentProgressMapper {
    public static TreatmentProgressDto mapper(TreatmentProgressUpdate treatmentProgressUpdate){
        TreatmentProgressDto treatmentProgressDto = new TreatmentProgressDto();

        treatmentProgressDto.setId(treatmentProgressUpdate.getId().toString());
        treatmentProgressDto.setSlug(treatmentProgressUpdate.getSlug());
        treatmentProgressDto.setPatientID(treatmentProgressUpdate.getPatientID());
        treatmentProgressDto.setProgress(treatmentProgressUpdate.getProgress());
        treatmentProgressDto.setVisitType(treatmentProgressUpdate.getVisitType());
        treatmentProgressDto.setAlignerTracking(treatmentProgressUpdate.getAlignerTracking());
        treatmentProgressDto.setClickable(treatmentProgressUpdate.isClickable());
        treatmentProgressDto.setNotes(treatmentProgressUpdate.getNotes());
        if(treatmentProgressUpdate.getPhotos()!=null) treatmentProgressDto.setPhotos(treatmentProgressUpdate.getPhotos().stream().map(S3ImageObject::getURL).toList());
        treatmentProgressDto.setCreatedOn(treatmentProgressUpdate.getCreatedOn());

        return treatmentProgressDto;
    }

    public static TreatmentProgressListDto mapperListDto(TreatmentProgressUpdate treatmentProgressUpdate){
        TreatmentProgressListDto treatmentProgressListDto = new TreatmentProgressListDto();

        treatmentProgressListDto.setId(treatmentProgressUpdate.getId().toString());
        treatmentProgressListDto.setSlug(treatmentProgressUpdate.getSlug());
        treatmentProgressListDto.setPatientID(treatmentProgressUpdate.getPatientID());
        treatmentProgressListDto.setProgress(treatmentProgressUpdate.getProgress());
        treatmentProgressListDto.setClickable(treatmentProgressUpdate.isClickable());
        treatmentProgressListDto.setCreatedOn(treatmentProgressUpdate.getCreatedOn());

        return treatmentProgressListDto;
    }
}
