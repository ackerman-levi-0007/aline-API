package com.aline.aline.services.Impl;

import com.aline.aline.dao.IDentalDetailsMappingDao;
import com.aline.aline.entities.User;
import com.aline.aline.enums.UserRole;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import com.aline.aline.services.IDentalDetailsMappingService;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentalDetailsMappingService implements IDentalDetailsMappingService {

    private final IDentalDetailsMappingDao patientDentalDetailsMappingDao;

    @Override
    public List<Integer> getAllRebootIds(String patientID) {
        return this.patientDentalDetailsMappingDao.getAllRebootIds(patientID);
    }

    @Override
    public PatientTreatmentPlanMapping getPlanMapping(String patientID, int rebootID) {
        PatientTreatmentPlanMapping patientTreatmentPlanMapping = this.patientDentalDetailsMappingDao.getPlanMapping(patientID, rebootID);

        User user = SecurityUtils.getLoggedInUser();
        if(user.getRole().contains(UserRole.ROLE_CLINIC) || user.getRole().contains(UserRole.ROLE_DOCTOR)){
            patientTreatmentPlanMapping.setTreatmentPlanDraft(null);
        }

        return patientTreatmentPlanMapping;
    }
}
