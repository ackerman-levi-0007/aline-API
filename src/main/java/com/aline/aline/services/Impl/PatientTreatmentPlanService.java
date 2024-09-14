package com.aline.aline.services.Impl;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.customMapper.PatientTreatmentPlanMapper;
import com.aline.aline.dao.IPatientDentalDetailsMappingDao;
import com.aline.aline.dao.IPatientTreatmentPlanDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.enums.TreatmentPlanType;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import com.aline.aline.services.IPatientTreatmentPlanService;
import com.aline.aline.services.helpers.PatientHelperService;
import com.aline.aline.utilities.CommonUtils;
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
    private final PatientTreatmentPlanMapper patientTreatmentPlanMapper;
    private final ModelMapper modelMapper;

    @Override
    public PatientTreatmentPlanDto getTreatmentPlan(String patientID, int rebootID, String planID, TreatmentPlanType treatmentPlanType) throws BadRequestException {

        PatientTreatmentPlanDto patientTreatmentPlanDto;

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

    @Override
    public void createDraft(String patientID, int rebootID, PatientTreatmentPlanDto patientTreatmentPlanDto) {
        patientHelperService.checkLoggedInUserPermissionForPatientID(patientTreatmentPlanDto.getPatientID());

        PatientTreatmentPlan  patientTreatmentPlan = this.modelMapper.map(patientTreatmentPlanDto, PatientTreatmentPlan.class);

        PatientDentalDetailsMapping patientDentalDetailsMapping = CommonUtils.getPatientPlanMapping();

        patientTreatmentPlan.setLabel(new StringBuilder().append("Option-").append(patientDentalDetailsMapping.getTreatmentPlanDraft().getTreatmentPlans().size()).toString());

        PatientTreatmentPlanDraft savedPatientTreatmentPlan = this.patientTreatmentPlanDao
                .saveDraft(patientTreatmentPlan);

        TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
        treatmentPlanObject.setId(savedPatientTreatmentPlan.getId().toString());
        treatmentPlanObject.setStatus(TreatmentPlanStatus.draft);

        this.patientDentalDetailsMappingDao.addUnsavedDraftTreatmentPlanID(
                savedPatientTreatmentPlan.getPatientID(),
                treatmentPlanObject,
                0
        );
    }

    @Override
    public void updateDraft(String patientID, int rebootID, PatientTreatmentPlanDto patientTreatmentPlanDto) {
        patientHelperService.checkLoggedInUserPermissionForPatientIDDentalDetails(patientTreatmentPlanDto.getPatientID());
        this.patientTreatmentPlanDao.updateDraft(patientID, rebootID, patientTreatmentPlanDto);
    }

    @Override
    public void sendPlanModification(String patientID, int rebootID, String draftID) {
        patientHelperService.checkLoggedInUserPermissionForPatientIDDentalDetails(patientID);
        this.patientTreatmentPlanDao.savePlan(patientID, rebootID , draftID);
    }
}
