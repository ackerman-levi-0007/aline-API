package com.aline.aline.services.helpers;

import com.aline.aline.cache.ThreadLocalCache;
import com.aline.aline.dao.IPatientDao;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PatientHelperService {

    private final IPatientDao patientDao;
    private final IPatientDentalDetailsMappingDao patientDentalDetailsMappingDao;
    private final IUserDao userDao;

    public void checkLoggedInUserPermissionForPatientID(String patientID){
        GetPatientDto patientDto = this.patientDao.getPatientByID(patientID);
    }

    public void checkLoggedInUserPermissionForPatientIDDentalDetails(String patientID){
        String loggedInUserID = Objects.requireNonNull(SecurityUtils.getCurrentUserUserID());
        GetPatientDto patientDto = this.patientDao.getPatientByID(patientID);
    }

    public void checkLoggedInUserPermission(String patientID, int rebootID) {
        PatientDentalDetailsMapping patientDentalDetailsMapping = this.patientDentalDetailsMappingDao
                .getPatientDentalDetailsMapping(patientID, rebootID);

        ThreadLocalCache.put("patientDentalDetailsMapping", patientDentalDetailsMapping);
    }
}
