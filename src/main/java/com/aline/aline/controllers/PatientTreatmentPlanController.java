package com.aline.aline.controllers;

import com.aline.aline.enums.TreatmentPlanType;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanDto;
import com.aline.aline.services.IPatientTreatmentPlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/treatmentPlan")
@Tag(name = "PatientTreatmentPlanController", description = "This API provides the capability to search and modify treatment plan details of patients")
@CrossOrigin("*")
public class PatientTreatmentPlanController {

    private final IPatientTreatmentPlanService patientTreatmentPlanService;

    @PostMapping("/saveDraft/{patientID}/{rebootID}")
    public ResponseEntity<APIResponse> saveDraft(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlanDto
    ){
        this.patientTreatmentPlanService.createDraft(patientID, rebootID, patientTreatmentPlanDto);
        return new ResponseEntity<>(new APIResponse("Draft saved successfully !!!", true), HttpStatus.OK);
    }

    @PutMapping("/updateDraft/{patientID}/{rebootID}")
    public ResponseEntity<APIResponse> updateDraft(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlanDto
    ){
        this.patientTreatmentPlanService.updateDraft(patientID, rebootID, patientTreatmentPlanDto);
        return new ResponseEntity<>(new APIResponse("Draft updated successfully !!!", true), HttpStatus.OK);
    }

    @PutMapping("/sendPlanModification/{patientID}/{rebootID}/{draftID}")
    public ResponseEntity<APIResponse> sendPlanModification(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @PathVariable String draftID
    ) {
        this.patientTreatmentPlanService.sendPlanModification(patientID, rebootID, draftID);
        return new ResponseEntity<>(new APIResponse("Treatment plan shared successfully !!!", true), HttpStatus.OK);
    }

    @GetMapping("/getPlan/{patientID}/{planID}/{rebootID}")
    public ResponseEntity<PatientTreatmentPlanDto> getPlan(
            @PathVariable String patientID,
            @PathVariable String planID,
            @PathVariable int rebootID,
            @RequestParam TreatmentPlanType planType
     ) throws BadRequestException {
        PatientTreatmentPlanDto patientTreatmentPlan = this.patientTreatmentPlanService
                .getTreatmentPlan(patientID, rebootID, planID, planType);
        return new ResponseEntity<>(patientTreatmentPlan, HttpStatus.OK);
    }

    @GetMapping("/getAllPlanForPatientID/{patientID}")
    public ResponseEntity<List<PatientTreatmentPlanDto>> getAllPlanForPatientID(
            @PathVariable String patientID
    ){
        List<PatientTreatmentPlanDto> patientTreatmentPlans = this.patientTreatmentPlanService.getAllTreatmentPlanForPatientID(patientID);
        return new ResponseEntity<>(patientTreatmentPlans, HttpStatus.OK);
    }

    @PutMapping("/approvePlan/{patientID}/{rebootID}/{planID}")
    public ResponseEntity<APIResponse> approvePlan(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @PathVariable String planID
    ) {
        this.patientTreatmentPlanService.approvePlan(patientID, rebootID, planID);
        return new ResponseEntity<>(new APIResponse("Plan approved", true), HttpStatus.OK);
    }

    @PutMapping("/planRequestModification/{patientID}/{rebootID}/{planID}")
    public ResponseEntity<APIResponse> planRequestModification(
            @PathVariable String patientID,
            @PathVariable int rebootID,
            @PathVariable String planID
    ) {
        this.patientTreatmentPlanService.planRequestModification(patientID, rebootID, planID);
        return new ResponseEntity<>(new APIResponse("Request for modification is initialized", true), HttpStatus.OK);
    }
}
