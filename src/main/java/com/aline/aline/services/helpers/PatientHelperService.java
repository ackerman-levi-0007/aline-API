package com.aline.aline.services.helpers;

import com.aline.aline.dao.IPatientDao;
import com.aline.aline.dao.IUserDao;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PatientHelperService {

    private final IPatientDao patientDao;
    private final IUserDao userDao;

    public void checkLoggedInUserPermissionForPatientID(String patientID){
        String loggedInUserID = Objects.requireNonNull(SecurityUtils.getCurrentUserUserID()).toString();
        UserDto loggedInUser = this.userDao.getUserByID(loggedInUserID);
        GetPatientDto patientDto = this.patientDao.getPatientByID(patientID, loggedInUser);
    }
}
