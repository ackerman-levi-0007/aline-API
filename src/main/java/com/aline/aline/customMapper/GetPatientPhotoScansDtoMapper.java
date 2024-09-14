package com.aline.aline.customMapper;

import com.aline.aline.commonEntitiesObjects.S3ImageObject;
import com.aline.aline.entities.PatientPhotoScans;
import com.aline.aline.payload.PatientPhotoScans.GetPatientPhotoScansDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GetPatientPhotoScansDtoMapper implements Function<PatientPhotoScans, GetPatientPhotoScansDto> {
    @Override
    public GetPatientPhotoScansDto apply(PatientPhotoScans patientPhotoScans) {
        return new GetPatientPhotoScansDto(
                    patientPhotoScans.getPatientID(),
                    patientPhotoScans.getProfilePhoto().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getExtFront().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getExtSide().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getExtOblique().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getIntFront().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getIntSideLeft().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getIntFrontRight().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getIntMaxilla().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getIntMandible().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getOpg().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getCep().stream().map(S3ImageObject::getURL).toList(),
                    patientPhotoScans.getScans().stream().map(S3ImageObject::getURL).toList()
        );
    }
}
