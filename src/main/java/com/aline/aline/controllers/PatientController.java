package com.aline.aline.controllers;

import com.aline.aline.entities.Patient;
import com.aline.aline.enums.PatientStatus;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.PageDto;
import com.aline.aline.payload.Patient.*;
import com.aline.aline.services.IPatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aline/patient")
@Tag(name = "PatientController", description = "This API provides the capability to search and modify details of patients")
@CrossOrigin("*")
public class PatientController {

    private final IPatientService patientService;

    @PostMapping("/createPatient")
    public ResponseEntity<GetPatientDto> createPatient(
            @RequestBody Patient patient
    ) throws BadRequestException {
        GetPatientDto getPatientDto = this.patientService.createPatient(patient);
        return new ResponseEntity<>(getPatientDto, HttpStatus.OK);
    }

    @PostMapping("/getAllPatients")
    public ResponseEntity<Page<GetPatientDto>> getAllPatients(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = Integer.MAX_VALUE+"") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "DESC") String sortDir,
            @RequestBody FilterPatientDto filterPatientDto
            ){
        PageDto pageDto = new PageDto(pageNumber, pageSize, sortBy, sortDir);
        Page<GetPatientDto> getPatientDtoList = this.patientService.getAllPatients(pageDto, filterPatientDto);
        return new ResponseEntity<>(getPatientDtoList, HttpStatus.OK);
    }

    @GetMapping("/getPatientByID/{patientID}")
    public ResponseEntity<GetPatientDto> getPatientByID(
            @PathVariable String patientID
    ){
        GetPatientDto patientDto = this.patientService.getPatientByID(patientID);
        return new ResponseEntity<>(patientDto, HttpStatus.OK);
    }

    @PutMapping("/updatePatient")
    public ResponseEntity<GetPatientDto> updatePatient(
            @RequestBody UpdatePatientDto patient
    ) throws BadRequestException {
        GetPatientDto updatedPatient = this.patientService.updatePatient(patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @GetMapping("/getStatusValuesForPatient")
    public ResponseEntity<List<String>> getStatusValuesForPatient(){
        List<String> patientStatus = List.of(Arrays.toString(PatientStatus.values()));
        return new ResponseEntity<>(patientStatus, HttpStatus.OK);
    }

    @PutMapping("/updatePatientStatus")
    public ResponseEntity<APIResponse> updatePatientStatus(
            @RequestBody UpdatePatientStatusDto patientStatus
    ){
        this.patientService.updatePatientStatus(patientStatus);
        return new ResponseEntity<>(new APIResponse("Patient status updated successfully!!!", true), HttpStatus.OK);
    }

    @DeleteMapping("/deletePatient/{patientID}")
    public ResponseEntity<APIResponse> deletePatient(
            @PathVariable String patientID
    ){
        this.patientService.deletePatient(patientID);
        return new ResponseEntity<>(new APIResponse("Patient deleted successfully!!!", true), HttpStatus.OK);
    }
}
