package com.aline.aline.services;

import com.aline.aline.entities.User;
import com.aline.aline.payload.User.UserCreationDto;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.payload.User.UserWithDetailsDto;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    UserDto createUser(User user, String parentID) throws BadRequestException;
    UserDto updateUser(String userID, UserDto userDto);
    UserDto getUserByID(String userID);
    void deleteUserByID(String userID);
    List<UserDto> getAllUsers();
    List<UserWithDetailsDto> getAllUsersWithDetails();
    UserWithDetailsDto createUserWithDetails(UserCreationDto userCreationDto, String parentID) throws BadRequestException;
}
