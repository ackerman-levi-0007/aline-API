package com.aline.aline.controllers;

import com.aline.aline.entities.TreatmentProgressUpdate;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.TreatmentProgress.TreatmentProgressDto;
import com.aline.aline.services.ITreatmentProgressUpdateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aline/treatmentProgress")
@Tag(name = "TreatmentProgressUpdateController", description = "This API provides the capability to search and modify treatment progress for patients")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TreatmentProgressUpdateController {

    private final ITreatmentProgressUpdateService treatmentProgressUpdateService;

    @PostMapping("/createProgress/{patientID}")
    public ResponseEntity<APIResponse> createProgress(
            @PathVariable String patientID,
            @RequestBody TreatmentProgressUpdate treatmentProgressUpdate
    ) {
        this.treatmentProgressUpdateService.createTreatmentProgress(patientID, treatmentProgressUpdate);
        return new ResponseEntity<>(new APIResponse("Treatment progress created", true), HttpStatus.OK);
    }

    @GetMapping("/getAllProgress/{patientID}")
    public ResponseEntity<List<TreatmentProgressDto>> getAllProgress(
            @PathVariable String patientID
    ){
        List<TreatmentProgressDto> treatmentProgressUpdateList =
                this.treatmentProgressUpdateService.getAllTreatmentProgress(patientID);
        return new ResponseEntity<>(treatmentProgressUpdateList, HttpStatus.OK);
    }

    @GetMapping("/getProgress/{patientID}/{id}")
    public ResponseEntity<TreatmentProgressDto> getProgress(
            @PathVariable String patientID,
            @PathVariable String id
    ){
        TreatmentProgressDto treatmentProgressUpdate =
                this.treatmentProgressUpdateService.getTreatmentProgress(patientID, id);
        return new ResponseEntity<>(treatmentProgressUpdate, HttpStatus.OK);
    }

    @PutMapping("/updateProgress/{patientID}")
    public ResponseEntity<APIResponse> updateProgress(
            @PathVariable String patientID,
            @RequestBody TreatmentProgressUpdate treatmentProgressUpdate
    ){
        this.treatmentProgressUpdateService.updateTreatmentProgress(patientID, treatmentProgressUpdate);
        return new ResponseEntity<>(new APIResponse("Treatment progress updated", true), HttpStatus.OK);
    }

    @DeleteMapping("/deleteProgress/{patientID}/{id}")
    public ResponseEntity<APIResponse> deleteProgress(
            @PathVariable String patientID,
            @PathVariable String id
    ){
        this.treatmentProgressUpdateService.deleteTreatmentProgress(patientID, id);
        return new ResponseEntity<>(new APIResponse("Treatment progress deleted", true), HttpStatus.OK);
    }

}
