package com.aline.aline.services.Impl;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
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

    private final IPatientDentalDetailsMappingDao patientDentalDetailsMappingDao;

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

    @Override
    public void addPatientTreatmentPlanID(String patientID, TreatmentPlanObject treatmentPlanObject, int rebootID, String draftID) {
        this.patientDentalDetailsMappingDao.addPatientTreatmentPlanID(patientID, treatmentPlanObject, rebootID, draftID);
    }

    @Override
    public void addUnsavedDraftTreatmentPlanID(String patientID, TreatmentPlanObject treatmentPlanObject, int rebootID) {
        this.patientDentalDetailsMappingDao.addUnsavedDraftTreatmentPlanID(patientID, treatmentPlanObject, rebootID);
    }

    @Override
    public void addTreatmentPlanToHistory(String patientID, List<TreatmentPlanObject> treatmentPlanObjects, int rebootID) {
        this.patientDentalDetailsMappingDao.addTreatmentPlanToHistory(patientID, treatmentPlanObjects, rebootID);
    }

    @Override
    public void moveTreatmentPlanToHistory(String patientID, int rebootID) {
        this.patientDentalDetailsMappingDao.moveTreatmentPlanToHistory(patientID, rebootID);
    }
}
