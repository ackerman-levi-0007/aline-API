package com.aline.aline.controllers;

import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.ClinicDoctorRelationShip.PostDto.CreateClinicDoctorRelationShip;
import com.aline.aline.services.IClinicDoctorRelationshipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/aline/clinicDoctorRelationship")
@Tag(name = "ClinicDoctorRelationshipController", description = "This API provides the capability to search and modify Clinic Doctor Relationship")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ClinicDoctorRelationshipController {

    private final IClinicDoctorRelationshipService clinicDoctorRelationshipService;

    @PostMapping("/addExistingDoctorToClinic")
    public ResponseEntity<APIResponse> addExistingDoctorToClinic(
            @RequestBody CreateClinicDoctorRelationShip clinicDoctorRelationShip
    ) throws BadRequestException {
        this.clinicDoctorRelationshipService.addExistingDoctorToClinic(clinicDoctorRelationShip);
        return new ResponseEntity<>(new APIResponse("Doctor has been added successfully to the clinic" ,true)
                , HttpStatus.OK);
    }
}
