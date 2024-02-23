package com.aline.aline.controllers;

import com.aline.aline.entities.User;
import com.aline.aline.payload.APIResponse;
import com.aline.aline.payload.UserDto;
import com.aline.aline.services.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aline/user")
@Tag(name = "UserController", description = "This API provides the capability to search and modify details of user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody User user){
        UserDto savedUser = this.userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PostMapping("/createUser/{parentID}")
    public ResponseEntity<UserDto> createUser(@RequestBody User user, @PathVariable String parentID){
        UserDto savedUser = this.userService.createUser(user, parentID);
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
}
