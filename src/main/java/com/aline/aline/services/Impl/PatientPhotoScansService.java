package com.aline.aline.services.Impl;

import com.aline.aline.customMapper.GetPatientPhotoScansDtoMapper;
import com.aline.aline.dao.IPatientDao;
import com.aline.aline.dao.IPhotoScansDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.entities.PatientPhotoScans;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientPhotoScans.GetPatientPhotoScansDto;
import com.aline.aline.services.IPatientPhotoScansService;
import com.aline.aline.services.helpers.PatientHelperService;
import com.aline.aline.utilities.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PatientPhotoScansService implements IPatientPhotoScansService {

    private final IPhotoScansDao patientPhotoScansDao;
    private final PatientHelperService patientHelperService;
    private final GetPatientPhotoScansDtoMapper getPatientPhotoScansDtoMapper;
    private final IPatientDao patientDao;

    @Override
    public GetPatientPhotoScansDto updatePatientPhotoScans(PatientPhotoScans patientPhotoScans, int rebootID) {
        patientHelperService.checkLoggedInUserPermission(patientPhotoScans.getPatientID(), rebootID);
        GetPatientPhotoScansDto updatedPatientPhotoScans =
                this.patientPhotoScansDao.updatePatientPhotoScans(patientPhotoScans, rebootID);
        this.patientDao.updatePatientProfilePhoto(
                patientPhotoScans.getPatientID(), CommonUtils.getFirstOrNull(patientPhotoScans.getProfilePhoto()));
        return updatedPatientPhotoScans;
    }

    @Override
    public Object getPatientPhotoScansByPatientID(String patientID, int rebootID) {
        patientHelperService.checkLoggedInUserPermission(patientID, rebootID);
        try{
            PatientDentalDetailsMapping patientDentalDetailsMapping = CommonUtils.getPatientPlanMapping();
            return this.patientPhotoScansDao.getPatientPhotoScans(patientDentalDetailsMapping.getPhotoScansId());
        }
        catch (ResourceNotFoundException ex){
            return new HashMap<>();
        }
    }

    @Override
    public GetPatientPhotoScansDto savePatientPhotoScans(PatientPhotoScans patientPhotoScans, int rebootID) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientPhotoScans.getPatientID());
        PatientPhotoScans savedPatientPhotoScans = this.patientPhotoScansDao.savePatientPhotoScans(patientPhotoScans, rebootID);
        this.patientDao.updatePatientProfilePhoto(
                patientPhotoScans.getPatientID(), CommonUtils.getFirstOrNull(patientPhotoScans.getProfilePhoto()));
        return this.getPatientPhotoScansDtoMapper.apply(savedPatientPhotoScans);
    }
}
