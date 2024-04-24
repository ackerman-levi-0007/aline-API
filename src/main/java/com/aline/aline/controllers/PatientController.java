package com.aline.aline.controllers;

import com.aline.aline.entities.Patient;
import com.aline.aline.payload.Patient.GetPatientDto;
import com.aline.aline.services.IPatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ){
        GetPatientDto getPatientDto = this.patientService.createPatient(patient);
        return new ResponseEntity<>(getPatientDto, HttpStatus.OK);
    }

    @GetMapping("/getAllPatients")
    public ResponseEntity<List<GetPatientDto>> getAllPatients(){
        List<GetPatientDto> getPatientDtoList = this.patientService.getAllPatients();
        return new ResponseEntity<>(getPatientDtoList, HttpStatus.OK);
    }

}
