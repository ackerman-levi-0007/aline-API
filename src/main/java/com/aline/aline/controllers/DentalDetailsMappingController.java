package com.aline.aline.controllers;

import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.PatientTreatmentPlan.PatientTreatmentPlanMapping;
import com.aline.aline.services.IDentalDetailsMappingService;
import com.aline.aline.services.IRebootService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/dentalDetailsMapping")
@Tag(name = "DentalDetailsMappingController", description = "This API provides the capability to search patient dental details mapping")
@CrossOrigin("*")
public class DentalDetailsMappingController {

    private final IDentalDetailsMappingService patientDentalDetailsMappingService;
    private final IRebootService rebootService;

    @GetMapping("/getAllRebootIds/{patientID}")
    public ResponseEntity<List<Integer>> getAllRebootIds(
            @PathVariable String patientID
    ) {
        List<Integer> rebootIds =
                this.patientDentalDetailsMappingService.getAllRebootIds(patientID);
        return new ResponseEntity<>(rebootIds, HttpStatus.OK);
    }

    @GetMapping("/getPlanMapping/{patientID}/{rebootID}")
    public ResponseEntity<PatientTreatmentPlanMapping> getPlanMapping(
            @PathVariable String patientID,
            @PathVariable int rebootID
    ) {
        PatientTreatmentPlanMapping patientTreatmentPlanMapping =
                this.patientDentalDetailsMappingService.getPlanMapping(patientID, rebootID);
        return new ResponseEntity<>(patientTreatmentPlanMapping, HttpStatus.OK);
    }

    @PostMapping("/createReboot/{patientID}")
    public ResponseEntity<APIResponse> createReboot(
            @PathVariable String patientID
    ){
         this.rebootService.createReboot(patientID);
        return new ResponseEntity<>(new APIResponse("Reboot created successfully!!!", true), HttpStatus.OK);
    }
}
