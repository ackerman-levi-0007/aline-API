package com.aline.aline.services.Impl;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.CustomMapper.PatientTreatmentPlanDraftMapper;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.dao.IPatientTreatmentPlanDao;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanHistory;
import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import com.aline.aline.services.IPatientTreatmentPlanService;
import com.aline.aline.services.helpers.PatientHelperService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientTreatmentPlanService implements IPatientTreatmentPlanService {

    private final PatientHelperService patientHelperService;
    private final IPatientTreatmentPlanDao patientTreatmentPlanDao;
    private final IPatientDentalDetailsMappingDao patientDentalDetailsMappingDao;
    private final PatientTreatmentPlanDraftMapper patientTreatmentPlanDraftMapper;
    private final ModelMapper modelMapper;

    @Override
    public PatientTreatmentPlanDto createTreatmentPlan(PatientTreatmentPlanDto patientTreatmentPlanDto) {
        patientHelperService.checkLoggedInUserPermissionForPatientIDDentalDetails(patientTreatmentPlanDto.getPatientID());
        PatientTreatmentPlan patientTreatmentPlan = this.modelMapper.map(patientTreatmentPlanDto, PatientTreatmentPlan.class);
        PatientTreatmentPlan savedPatientTreatmentPlan = this.patientTreatmentPlanDao.createTreatmentPlan(patientTreatmentPlan);

        TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
        treatmentPlanObject.setId(savedPatientTreatmentPlan.getId().toString());
        treatmentPlanObject.setStatus(TreatmentPlanStatus.shared);

        this.patientDentalDetailsMappingDao.addPatientTreatmentPlanIDForPatientID(
                savedPatientTreatmentPlan.getPatientID(),
                treatmentPlanObject
        );
        return this.modelMapper.map(savedPatientTreatmentPlan, PatientTreatmentPlanDto.class);
    }

    @Override
    public PatientTreatmentPlanDto saveDraftForTreatmentPlan(PatientTreatmentPlanDto patientTreatmentPlanDto) {
        patientHelperService.checkLoggedInUserPermissionForPatientIDDentalDetails(patientTreatmentPlanDto.getPatientID());
        PatientTreatmentPlan patientTreatmentPlan = this.modelMapper.map(patientTreatmentPlanDto, PatientTreatmentPlan.class);
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = this.patientTreatmentPlanDao.saveDraftForTreatmentPlan(patientTreatmentPlan);

        TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
        treatmentPlanObject.setId(patientTreatmentPlanDraft.getId().toString());
        treatmentPlanObject.setStatus(TreatmentPlanStatus.draft);

        this.patientDentalDetailsMappingDao.addUnsavedDraftTreatmentPlanIDForPatientID(
            patientTreatmentPlanDraft.getPatientID(),
            treatmentPlanObject
        );
        return this.patientTreatmentPlanDraftMapper.DtoMapper(patientTreatmentPlanDraft);
    }

    @Override
    public void sendTreatmentPlanModificationToDoctor(String patientID, String treatmentPlanID, PatientTreatmentPlanDto patientTreatmentPlanDto) throws BadRequestException {
        try{
            PatientTreatmentPlan getpatientTreatmentPlan = this.patientTreatmentPlanDao.getTreatmentPlan(patientID, treatmentPlanID);
            PatientTreatmentPlan patientTreatmentPlan = this.modelMapper.map(patientTreatmentPlanDto, PatientTreatmentPlan.class);
            this.patientTreatmentPlanDao.updateTreatmentPlan(patientID, treatmentPlanID, getpatientTreatmentPlan, patientTreatmentPlan);
            this.patientTreatmentPlanDao.moveTreatmentPlanToHistory(getpatientTreatmentPlan);
        } catch (ResourceNotFoundException ex){
            createTreatmentPlan(patientTreatmentPlanDto);
        }
    }

    @Override
    public PatientTreatmentPlanDto getTreatmentPlan(String patientID, String treatmentPlanID) {
        PatientTreatmentPlan patientTreatmentPlan = this.patientTreatmentPlanDao.getTreatmentPlan(patientID, treatmentPlanID);
        return this.modelMapper.map(patientTreatmentPlan, PatientTreatmentPlanDto.class);
    }

    @Override
    public List<PatientTreatmentPlanDto> getAllTreatmentPlanForPatientID(String patientID) {
        List<PatientTreatmentPlan> patientTreatmentPlans = this.patientTreatmentPlanDao.getAllTreatmentPlanForPatientID(patientID);
        return patientTreatmentPlans.stream().map(x -> this.modelMapper.map(x, PatientTreatmentPlanDto.class)).toList();
    }

    @Override
    public PatientTreatmentPlanDto getTreatmentPlanDraft(String patientID, String treatmentPlanID) {
        PatientTreatmentPlan patientTreatmentPlan = this.patientTreatmentPlanDao.getTreatmentPlanDraft(patientID, treatmentPlanID);
        return this.modelMapper.map(patientTreatmentPlan, PatientTreatmentPlanDto.class);
    }

    @Override
    public List<String> getAllHistoricalVersionIDsForTreatmentPlan(String patientID, String treatmentPlanID) {
        List<PatientTreatmentPlanHistory> patientTreatmentPlanHistories = this.patientTreatmentPlanDao
                .getAllHistoricalVersionIDsForTreatmentPlan(patientID, treatmentPlanID);

        // Sort by createdDate descending and map to list of IDs
        List<PatientTreatmentPlanHistory> toSort = new ArrayList<>();
        for (PatientTreatmentPlanHistory history : patientTreatmentPlanHistories) {
            if (history.getCreatedOn() != null) {
                toSort.add(history);
            }
        }
        toSort.sort(Comparator.comparing(PatientTreatmentPlanHistory::getCreatedOn).reversed());
        List<String> ids = new ArrayList<>();
        for (PatientTreatmentPlanHistory history : toSort) {
            String id = history.getId().toString();
            ids.add(id);
        }
        return ids;
    }

    @Override
    public PatientTreatmentPlanDto getHistoricalTreatmentPlan(String patientID, String treatmentPlanID, String treatmentPlanVersionID) {
        PatientTreatmentPlan patientTreatmentPlan = this.patientTreatmentPlanDao.getHistoricalTreatmentPlan(patientID, treatmentPlanID, treatmentPlanVersionID);
        return this.modelMapper.map(patientTreatmentPlan, PatientTreatmentPlanDto.class);
    }
}
