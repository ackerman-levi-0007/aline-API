package com.aline.aline.services.Impl;

import com.aline.aline.CommonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.CustomMapper.PatientTreatmentPlanDraftMapper;
import com.aline.aline.CustomMapper.PatientTreatmentPlanMapper;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.dao.IPatientTreatmentPlanDao;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.enums.TreatmentPlanType;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import com.aline.aline.services.IPatientTreatmentPlanService;
import com.aline.aline.services.helpers.PatientHelperService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientTreatmentPlanService implements IPatientTreatmentPlanService {

    private final PatientHelperService patientHelperService;
    private final IPatientTreatmentPlanDao patientTreatmentPlanDao;
    private final IPatientDentalDetailsMappingDao patientDentalDetailsMappingDao;
    private final PatientTreatmentPlanDraftMapper patientTreatmentPlanDraftMapper;
    private final PatientTreatmentPlanMapper patientTreatmentPlanMapper;
    private final ModelMapper modelMapper;

    @Override
    public PatientTreatmentPlanDto createTreatmentPlan(PatientTreatmentPlanDto patientTreatmentPlanDto) {
        patientHelperService.checkLoggedInUserPermissionForPatientIDDentalDetails(patientTreatmentPlanDto.getPatientID());

        PatientTreatmentPlan patientTreatmentPlan = this.modelMapper.map(patientTreatmentPlanDto, PatientTreatmentPlan.class);
        PatientTreatmentPlan savedPatientTreatmentPlan = this.patientTreatmentPlanDao.createTreatmentPlan(patientTreatmentPlan);

        TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
        treatmentPlanObject.setId(savedPatientTreatmentPlan.getId().toString());
        treatmentPlanObject.setStatus(TreatmentPlanStatus.shared);

        this.patientDentalDetailsMappingDao.addPatientTreatmentPlanID(
                savedPatientTreatmentPlan.getPatientID(),
                treatmentPlanObject,
                0
        );
        return patientTreatmentPlanMapper.DtoMapper(patientTreatmentPlan);
    }

    @Override
    public PatientTreatmentPlanDto saveDraftForTreatmentPlan(PatientTreatmentPlanDto patientTreatmentPlanDto) {
        patientHelperService.checkLoggedInUserPermissionForPatientIDDentalDetails(patientTreatmentPlanDto.getPatientID());
        PatientTreatmentPlan patientTreatmentPlan = this.modelMapper.map(patientTreatmentPlanDto, PatientTreatmentPlan.class);
        PatientTreatmentPlanDraft patientTreatmentPlanDraft = this.patientTreatmentPlanDao.saveDraftForTreatmentPlan(patientTreatmentPlan);

        TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
        treatmentPlanObject.setId(patientTreatmentPlanDraft.getId().toString());
        treatmentPlanObject.setStatus(TreatmentPlanStatus.draft);

        this.patientDentalDetailsMappingDao.addUnsavedDraftTreatmentPlanID(
            patientTreatmentPlanDraft.getPatientID(),
            treatmentPlanObject,
            0
        );
        return this.patientTreatmentPlanDraftMapper.DtoMapper(patientTreatmentPlanDraft);
    }

    @Override
    public void sendTreatmentPlanModificationToDoctor(String patientID, String treatmentPlanID, PatientTreatmentPlanDto patientTreatmentPlanDto) throws BadRequestException {
        PatientTreatmentPlan getpatientTreatmentPlan = new PatientTreatmentPlan();
        try{
            getpatientTreatmentPlan = this.patientTreatmentPlanDao.getTreatmentPlan(patientID, treatmentPlanID);
        } catch (ResourceNotFoundException ex){
            createTreatmentPlan(patientTreatmentPlanDto);
        }
        PatientTreatmentPlan patientTreatmentPlan = this.modelMapper.map(patientTreatmentPlanDto, PatientTreatmentPlan.class);
        this.patientTreatmentPlanDao.updateTreatmentPlan(patientID, treatmentPlanID, getpatientTreatmentPlan, patientTreatmentPlan);
        this.patientTreatmentPlanDao.moveTreatmentPlanToHistory(getpatientTreatmentPlan);
    }

    @Override
    public PatientTreatmentPlanDto getTreatmentPlan(String patientID, int rebootID, String planID, TreatmentPlanType treatmentPlanType) throws BadRequestException {

        PatientTreatmentPlanDto patientTreatmentPlanDto = null;

        if(treatmentPlanType == TreatmentPlanType.LATEST){
            PatientTreatmentPlan patientTreatmentPlan = this.patientTreatmentPlanDao.getTreatmentPlan(patientID, planID);
            patientTreatmentPlanDto = patientTreatmentPlanMapper.DtoMapper(patientTreatmentPlan);
        } else if(treatmentPlanType == TreatmentPlanType.HISTORY){
            patientTreatmentPlanDto = this.patientTreatmentPlanDao.getHistoricalTreatmentPlan(patientID, planID);
        } else if(treatmentPlanType == TreatmentPlanType.DRAFT){
            patientTreatmentPlanDto = this.patientTreatmentPlanDao.getTreatmentPlanDraft(patientID, planID);
        } else {
            throw new BadRequestException("The given treatment plan type is not defined !!!");
        }

        return patientTreatmentPlanDto;
    }

    @Override
    public List<PatientTreatmentPlanDto> getAllTreatmentPlanForPatientID(String patientID) {
        List<PatientTreatmentPlan> patientTreatmentPlans = this.patientTreatmentPlanDao.getAllTreatmentPlanForPatientID(patientID);
        return patientTreatmentPlans.stream().map(x -> this.modelMapper.map(x, PatientTreatmentPlanDto.class)).toList();
    }
}
