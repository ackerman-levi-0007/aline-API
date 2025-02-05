package com.aline.aline.services.Impl;

import com.aline.aline.dao.ITreatmentProgressUpdateDao;
import com.aline.aline.entities.TreatmentProgressUpdate;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressDto;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressListDto;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressUpdateDto;
import com.aline.aline.services.ITreatmentProgressUpdateService;
import com.aline.aline.services.helpers.PatientHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentProgressUpdateService implements ITreatmentProgressUpdateService {

    private final ITreatmentProgressUpdateDao treatmentProgressUpdateDao;
    private final PatientHelperService patientHelperService;

    @Override
    public TreatmentProgressDto getTreatmentProgress(String patientID, String id) {
        this.patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        return this.treatmentProgressUpdateDao.getTreatmentProgress(id);
    }

    @Override
    public void createTreatmentProgress(String patientID, TreatmentProgressUpdate treatmentProgressUpdate) {
        this.patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        this.treatmentProgressUpdateDao.createTreatmentProgress(treatmentProgressUpdate, true);
    }

    @Override
    public void updateTreatmentProgress(String patientID, TreatmentProgressUpdateDto treatmentProgressUpdate) {
        this.patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        this.treatmentProgressUpdateDao.updateTreatmentProgress(treatmentProgressUpdate);
    }

    @Override
    public List<TreatmentProgressListDto> getAllTreatmentProgress(String patientID) {
        this.patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        return this.treatmentProgressUpdateDao.getAllTreatmentProgress(patientID);
    }

    @Override
    public void deleteTreatmentProgress(String patientID, String id) {
        this.patientHelperService.checkLoggedInUserPermissionForPatientID(patientID);
        this.treatmentProgressUpdateDao.deleteTreatmentProgress(id);
    }
}
