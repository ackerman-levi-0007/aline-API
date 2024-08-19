package com.aline.aline.controllers;

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
@RequestMapping("/api/v1/aline/patientTreatmentPlanController")
@Tag(name = "PatientTreatmentPlanController", description = "This API provides the capability to search and modify treatment plan details of patients")
@CrossOrigin("*")
public class PatientTreatmentPlanController {

    private final IPatientTreatmentPlanService patientTreatmentPlanService;

    @PostMapping("/createTreatmentPlan")
    public ResponseEntity<PatientTreatmentPlanDto> createTreatmentPlan(
            @RequestHeader String patientID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlan
    ){
        PatientTreatmentPlanDto savedPatientTreatmentPlan = this.patientTreatmentPlanService.createTreatmentPlan(patientTreatmentPlan);
        return new ResponseEntity<>(savedPatientTreatmentPlan, HttpStatus.OK);
    }

    @PostMapping("/saveDraftForTreatmentPlan")
    public ResponseEntity<APIResponse> saveDraftForTreatmentPlan(
            @RequestHeader String patientID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlan
    ){
        this.patientTreatmentPlanService.saveDraftForTreatmentPlan(patientTreatmentPlan);
        return new ResponseEntity<>(new APIResponse("Draft saved successfully !!!", true), HttpStatus.OK);
    }

    @PutMapping("/sendTreatmentPlanModificationToDoctor")
    public ResponseEntity<APIResponse> sendTreatmentPlanModificationToDoctor(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlan
    ) throws BadRequestException {
        this.patientTreatmentPlanService.sendTreatmentPlanModificationToDoctor(patientID, treatmentPlanID, patientTreatmentPlan);
        return new ResponseEntity<>(new APIResponse("Treatment plan shared successfully to doctor !!!", true), HttpStatus.OK);
    }

    @GetMapping("/getTreatmentPlan")
    public ResponseEntity<PatientTreatmentPlanDto> getTreatmentPlan(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID
    ){
        PatientTreatmentPlanDto patientTreatmentPlan = this.patientTreatmentPlanService.getTreatmentPlan(patientID, treatmentPlanID);
        return new ResponseEntity<>(patientTreatmentPlan, HttpStatus.OK);
    }

    @GetMapping("/getAllTreatmentPlanForPatientID")
    public ResponseEntity<List<PatientTreatmentPlanDto>> getAllTreatmentPlanForPatientID(
            @RequestHeader String patientID
    ){
        List<PatientTreatmentPlanDto> patientTreatmentPlans = this.patientTreatmentPlanService.getAllTreatmentPlanForPatientID(patientID);
        return new ResponseEntity<>(patientTreatmentPlans, HttpStatus.OK);
    }

    @GetMapping("/getTreatmentPlanDraft")
    public ResponseEntity<PatientTreatmentPlanDto> getTreatmentPlanDraft(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID
    ){
        PatientTreatmentPlanDto patientTreatmentPlanDraft = this.patientTreatmentPlanService.getTreatmentPlanDraft(patientID, treatmentPlanID);
        return new ResponseEntity<>(patientTreatmentPlanDraft, HttpStatus.OK);
    }

    @GetMapping("/getAllHistoricalVersionIDsForTreatmentPlan")
    public ResponseEntity<List<String>> getAllHistoricalVersionIDsForTreatmentPlan(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID
    ){
        List<String> ids = this.patientTreatmentPlanService.getAllHistoricalVersionIDsForTreatmentPlan(patientID, treatmentPlanID);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/getHistoricalTreatmentPlan")
    public ResponseEntity<PatientTreatmentPlanDto> getHistoricalTreatmentPlan(
            @RequestHeader String patientID,
            @RequestHeader String treatmentPlanID,
            @RequestHeader String treatmentPlanVersionID
    ){
        PatientTreatmentPlanDto patientTreatmentPlan = this.patientTreatmentPlanService.getHistoricalTreatmentPlan(patientID, treatmentPlanID, treatmentPlanVersionID);
        return new ResponseEntity<>(patientTreatmentPlan, HttpStatus.OK);
    }
}