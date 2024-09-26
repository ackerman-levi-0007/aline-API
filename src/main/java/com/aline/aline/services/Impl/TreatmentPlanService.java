package com.aline.aline.services.Impl;

import com.aline.aline.commonEntitiesObjects.TreatmentPlanObject;
import com.aline.aline.customMapper.PatientTreatmentPlanMapper;
import com.aline.aline.dao.IDentalDetailsMappingDao;
import com.aline.aline.dao.ITreatmentPlanDao;
import com.aline.aline.entities.PatientDentalDetailsMapping;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlan;
import com.aline.aline.entities.PatientTreatmentPlan.PatientTreatmentPlanDraft;
import com.aline.aline.entities.User;
import com.aline.aline.enums.TreatmentPlanStatus;
import com.aline.aline.enums.TreatmentPlanType;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ForbiddenException;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import com.aline.aline.services.ITreatmentPlanService;
import com.aline.aline.services.helpers.PatientHelperService;
import com.aline.aline.utilities.CommonUtils;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentPlanService implements ITreatmentPlanService {

    private final PatientHelperService patientHelperService;
    private final ITreatmentPlanDao patientTreatmentPlanDao;
    private final IDentalDetailsMappingDao patientDentalDetailsMappingDao;
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
        patientHelperService.checkLoggedInUserPermission(patientTreatmentPlanDto.getPatientID(), rebootID);

        PatientTreatmentPlan  patientTreatmentPlan = this.modelMapper.map(patientTreatmentPlanDto, PatientTreatmentPlan.class);

        PatientDentalDetailsMapping patientDentalDetailsMapping = CommonUtils.getPatientPlanMapping();

        int displayOrder = patientDentalDetailsMapping.getTreatmentPlanDraft() == null ?
                            1 : (patientDentalDetailsMapping.getTreatmentPlanDraft().getTreatmentPlans().size() + 1);

        patientTreatmentPlan.setLabel("Option-" + displayOrder);

        PatientTreatmentPlanDraft savedPatientTreatmentPlan = this.patientTreatmentPlanDao
                .saveDraft(patientTreatmentPlan);

        TreatmentPlanObject treatmentPlanObject = new TreatmentPlanObject();
        treatmentPlanObject.setId(savedPatientTreatmentPlan.getId().toString());
        treatmentPlanObject.setLabel(savedPatientTreatmentPlan.getLabel());
        treatmentPlanObject.setDisplayOrder(displayOrder);
        treatmentPlanObject.setStatus(TreatmentPlanStatus.draft);

        this.patientDentalDetailsMappingDao.addUnsavedDraftTreatmentPlanID(
                savedPatientTreatmentPlan.getPatientID(),
                treatmentPlanObject,
                0
        );
    }

    @Override
    public void updateDraft(String patientID, int rebootID, PatientTreatmentPlanDto patientTreatmentPlanDto) {
        patientHelperService.checkLoggedInUserPermission(patientTreatmentPlanDto.getPatientID(), rebootID);
        this.patientTreatmentPlanDao.updateDraft(patientID, rebootID, patientTreatmentPlanDto);
    }

    @Override
    public void sendPlanModification(String patientID, int rebootID, String draftID) {
        this.patientHelperService.checkLoggedInUserPermission(patientID, rebootID);
        this.patientTreatmentPlanDao.savePlan(patientID, rebootID , draftID);
    }

    @SneakyThrows
    @Override
    public void approvePlan(String patientID, int rebootID, String planID) {
        validatePlanStatusChangeRequest(patientID, rebootID, planID);
        this.patientDentalDetailsMappingDao.approvePlan(patientID, rebootID, planID);
    }

    @SneakyThrows
    @Override
    public void planRequestModification(String patientID, int rebootID, String planID) {
        validatePlanStatusChangeRequest(patientID, rebootID, planID);
        this.patientDentalDetailsMappingDao.planRequestModification(patientID, rebootID, planID);
    }

    /*****************************************************************************************
                                             Helpers
     ****************************************************************************************/

    private void validatePlanStatusChangeRequest(String patientID, int rebootID, String planID) throws BadRequestException {
        User loggedInUser = SecurityUtils.getLoggedInUser();

        if(loggedInUser.getRole().contains(UserRole.ROLE_LAB)){
            throw new ForbiddenException("User does not have rights for this action");
        }

        patientHelperService.checkLoggedInUserPermission(patientID, rebootID);

        PatientDentalDetailsMapping patientTreatmentPlanMapping = CommonUtils.getPatientPlanMapping();

//        if(patientTreatmentPlanMapping.getTreatmentPlanLatest() == null ||
//                patientTreatmentPlanMapping.getTreatmentPlanLatest().getTreatmentPlans().isEmpty()){
//            throw new BadRequestException("No latest treatment plan");
//        }
//        else{
//            if(patientTreatmentPlanMapping.getTreatmentPlanLatest().getTreatmentPlanStatus().equals(TreatmentPlanStatus.confirmed)){
//                throw new BadRequestException("Treatment plan is approved for the patient");
//            }
//            else {
//                if (patientTreatmentPlanMapping.getTreatmentPlanLatest()
//                        .getTreatmentPlans()
//                        .parallelStream()
//                        .noneMatch(plan -> plan.getId().equals(planID))
//                ) {
//                    throw new BadRequestException("No plan found for update");
//                }
//            }
//        }
    }

}
