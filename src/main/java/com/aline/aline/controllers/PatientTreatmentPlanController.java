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
@RequestMapping("/api/v1/aline/treatmentPlan")
@Tag(name = "PatientTreatmentPlanController", description = "This API provides the capability to search and modify treatment plan details of patients")
@CrossOrigin("*")
public class PatientTreatmentPlanController {

    private final IPatientTreatmentPlanService patientTreatmentPlanService;

    @PostMapping("/createPlan/{patientID}")
    public ResponseEntity<PatientTreatmentPlanDto> createPlan(
            @PathVariable String patientID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlan
    ){
        PatientTreatmentPlanDto savedPatientTreatmentPlan = this.patientTreatmentPlanService.createTreatmentPlan(patientTreatmentPlan);
        return new ResponseEntity<>(savedPatientTreatmentPlan, HttpStatus.OK);
    }

    @PostMapping("/saveDraft/{patientID}")
    public ResponseEntity<APIResponse> saveDraft(
            @PathVariable String patientID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlan
    ){
        this.patientTreatmentPlanService.saveDraftForTreatmentPlan(patientTreatmentPlan);
        return new ResponseEntity<>(new APIResponse("Draft saved successfully !!!", true), HttpStatus.OK);
    }

    @PutMapping("/sendPlanModificationToDoctor/{patientID}/{treatmentPlanID}")
    public ResponseEntity<APIResponse> sendPlanModificationToDoctor(
            @PathVariable String patientID,
            @PathVariable String treatmentPlanID,
            @RequestBody PatientTreatmentPlanDto patientTreatmentPlan
    ) throws BadRequestException {
        this.patientTreatmentPlanService.sendTreatmentPlanModificationToDoctor(patientID, treatmentPlanID, patientTreatmentPlan);
        return new ResponseEntity<>(new APIResponse("Treatment plan shared successfully to doctor !!!", true), HttpStatus.OK);
    }

    @GetMapping("/getPlan/{patientID}/{treatmentPlanID}")
    public ResponseEntity<PatientTreatmentPlanDto> getPlan(
            @PathVariable String patientID,
            @PathVariable String treatmentPlanID
    ){
        PatientTreatmentPlanDto patientTreatmentPlan = this.patientTreatmentPlanService.getTreatmentPlan(patientID, treatmentPlanID);
        return new ResponseEntity<>(patientTreatmentPlan, HttpStatus.OK);
    }

    @GetMapping("/getAllPlanForPatientID/{patientID}")
    public ResponseEntity<List<PatientTreatmentPlanDto>> getAllPlanForPatientID(
            @PathVariable String patientID
    ){
        List<PatientTreatmentPlanDto> patientTreatmentPlans = this.patientTreatmentPlanService.getAllTreatmentPlanForPatientID(patientID);
        return new ResponseEntity<>(patientTreatmentPlans, HttpStatus.OK);
    }

    @GetMapping("/getPlanDraft/{patientID}/{treatmentPlanID}")
    public ResponseEntity<PatientTreatmentPlanDto> getPlanDraft(
            @PathVariable String patientID,
            @PathVariable String treatmentPlanID
    ){
        PatientTreatmentPlanDto patientTreatmentPlanDraft = this.patientTreatmentPlanService.getTreatmentPlanDraft(patientID, treatmentPlanID);
        return new ResponseEntity<>(patientTreatmentPlanDraft, HttpStatus.OK);
    }

    @GetMapping("/getAllHistoricalVersionIDsForTreatmentPlan/{patientID}/{treatmentPlanID}")
    public ResponseEntity<List<String>> getAllHistoricalVersionIDsForTreatmentPlan(
            @PathVariable String patientID,
            @PathVariable String treatmentPlanID
    ){
        List<String> ids = this.patientTreatmentPlanService.getAllHistoricalVersionIDsForTreatmentPlan(patientID, treatmentPlanID);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @GetMapping("/getHistoricalPlan/{patientID}/{treatmentPlanID}/{treatmentPlanVersionID}")
    public ResponseEntity<PatientTreatmentPlanDto> getHistoricalTreatmentPlan(
            @PathVariable String patientID,
            @PathVariable String treatmentPlanID,
            @PathVariable String treatmentPlanVersionID
    ){
        PatientTreatmentPlanDto patientTreatmentPlan = this.patientTreatmentPlanService.getHistoricalTreatmentPlan(patientID, treatmentPlanID, treatmentPlanVersionID);
        return new ResponseEntity<>(patientTreatmentPlan, HttpStatus.OK);
    }
}
