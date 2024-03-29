package com.aline.aline.controllers;

import com.aline.aline.entities.User;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.User.UserCreationDto;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.payload.User.UserWithDetailsDto;
import com.aline.aline.services.IUserService;
import com.aline.aline.utilities.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/aline/user")
@Tag(name = "UserController", description = "This API provides the capability to search and modify details of user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final IUserService userService;

    @PostMapping("/createUserWithDetails")
    public ResponseEntity<UserWithDetailsDto> createUserWithDetails(
            @RequestBody UserCreationDto userCreationDto
    ) throws BadRequestException {
        UserWithDetailsDto savedUser = this.userService.createUserWithDetails(userCreationDto, null);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PostMapping("/createUserWithDetails/{parentID}")
    public ResponseEntity<UserWithDetailsDto> createUserWithDetails(
            @RequestBody UserCreationDto userCreationDto,
            @PathVariable String parentID
    ) throws BadRequestException {
        UserWithDetailsDto savedUser = this.userService.createUserWithDetails(userCreationDto, parentID);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }


    @PutMapping("/updateUser/{userID}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userID){
        UserDto updatedUser = this.userService.updateUser(userID, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/getUserByID/{userID}")
    public ResponseEntity<UserDto> getUserByID(@PathVariable String userID){
        UserDto user = userService.getUserByID(userID);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserByID/{userID}")
    public ResponseEntity<APIResponse> deleteUserByID(@PathVariable String userID){
        this.userService.deleteUserByID(userID);
        return new ResponseEntity<>(new APIResponse("User Deleted Successfully!!" ,true)
                                                , HttpStatus.OK);  
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtoList = this.userService.getAllUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/getSignedInUserInfo")
    public ResponseEntity<UserDto> getSignedInUserInfo(){
        String userIDForLoggedInUser = Objects.requireNonNull(SecurityUtils.getCurrentUserUserID()).toString();
        UserDto userDto = this.userService.getUserByID(userIDForLoggedInUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/getAllUsersWithDetails")
    public ResponseEntity<List<UserWithDetailsDto>> getAllUsersWithDetails(){
        List<UserWithDetailsDto> userDtoList = this.userService.getAllUsersWithDetails();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
}
