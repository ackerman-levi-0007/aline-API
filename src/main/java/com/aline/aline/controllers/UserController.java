package com.aline.aline.controllers;

import com.aline.aline.entities.User;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.PageDto;
import com.aline.aline.payload.User.PasswordChangeDto;
import com.aline.aline.payload.User.UserCreationDto;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.payload.User.UserWithDetailsDto;
import com.aline.aline.services.IUserService;
import com.aline.aline.utilities.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/aline/user")
@Tag(name = "UserController", description = "This API provides the capability to search and modify details of user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final IUserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(
            @RequestBody User user,
            @RequestParam(value = "parentID", required = false) String parentID

    ) throws BadRequestException {
        UserDto savedUser = this.userService.createUser(user, parentID);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PostMapping("/createUserWithDetails")
    public ResponseEntity<UserWithDetailsDto> createUserWithDetails(
            @RequestBody UserCreationDto userCreationDto,
            @RequestParam(value = "parentID", required = false) String parentID
    ) throws BadRequestException {
        UserWithDetailsDto savedUser = this.userService.createUserWithDetails(userCreationDto, parentID);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{userID}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userID){
        UserDto updatedUser = this.userService.updateUser(userID, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/updateUserWithDetails/{userID}")
    public ResponseEntity<UserWithDetailsDto> updateUserWithDetails(
            @RequestBody UserWithDetailsDto userCreationDto,
            @PathVariable String userID
    ){
        UserWithDetailsDto savedUser = this.userService.updateUserWithDetails(userCreationDto, userID);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @GetMapping("/getUserByID/{userID}")
    public ResponseEntity<UserDto> getUserByID(@PathVariable String userID){
        UserDto user = userService.getUserByID(userID);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserByID/{userID}")
    public ResponseEntity<APIResponse> deleteUserByID(@PathVariable String userID){
        this.userService.deleteUserByID(userID);
        return new ResponseEntity<>(new APIResponse("User deleted successfully!!" ,true)
                                                , HttpStatus.OK);  
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(value = "userID", required = false) String userID,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "role") String role,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = Integer.MAX_VALUE+"") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "DESC") String sortDir
    ) throws BadRequestException {
        PageDto pageDto = new PageDto(pageNumber, pageSize, sortBy, sortDir);
        Page<UserDto> userDtoList = this.userService.getUsers(userID, role, query, pageDto);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/getSignedInUserInfo")
    public ResponseEntity<UserDto> getSignedInUserInfo(){
        String userIDForLoggedInUser = Objects.requireNonNull(SecurityUtils.getCurrentUserUserID());
        UserDto userDto = this.userService.getUserByID(userIDForLoggedInUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/getAllUsersWithDetails")
    public ResponseEntity<Page<UserWithDetailsDto>> getAllUsersWithDetails(
            @RequestParam(value = "userID", required = false) String userID,
            @RequestParam(value = "role") String role,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = Integer.MAX_VALUE+"") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "DESC") String sortDir
    ) throws BadRequestException {
        PageDto pageDto = new PageDto(pageNumber, pageSize, sortBy, sortDir);
        Page<UserWithDetailsDto> userDtoList = this.userService.getUsersWithDetails(userID, role, query, pageDto);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PutMapping("/activeDeActiveUser")
    public ResponseEntity<APIResponse> activeDeActiveUser(
            @RequestParam(value = "userID") String userID,
            @RequestParam(value = "active") boolean status
    ) {
        this.userService.activeDeActiveUser(userID, status);
        return new ResponseEntity<>(new APIResponse("User de-activated successfully!!" ,true)
                , HttpStatus.OK);
    }

    @PutMapping("/resetPassword/{userID}")
    public ResponseEntity<APIResponse> resetPassword(
            @PathVariable String userID,
            @RequestBody PasswordChangeDto passwordChange
    ) throws BadRequestException {
        this.userService.changePassword(userID, passwordChange, "reset");
        return new ResponseEntity<>(new APIResponse("User password changed successfully" ,true)
                , HttpStatus.OK);
    }

    @PutMapping("/forgotPassword/{userID}")
    public ResponseEntity<APIResponse> forgotPassword(
            @PathVariable String userID,
            @RequestBody PasswordChangeDto passwordChange
    ) throws BadRequestException {
        this.userService.changePassword(userID, passwordChange, "forgot");
        return new ResponseEntity<>(new APIResponse("User password changed successfully" ,true)
                , HttpStatus.OK);
    }
}
