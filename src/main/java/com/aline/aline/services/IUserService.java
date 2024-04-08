package com.aline.aline.services;

import com.aline.aline.entities.User;
import com.aline.aline.payload.PageDto;
import com.aline.aline.payload.User.PasswordChangeDto;
import com.aline.aline.payload.User.UserCreationDto;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.payload.User.UserWithDetailsDto;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    UserDto createUser(User user, String parentID) throws BadRequestException;
    UserDto updateUser(String userID, UserDto userDto);
    UserDto getUserByID(String userID);
    void deleteUserByID(String userID);
    Page<UserDto> getUsers(String userID, String role, String query, PageDto pageDto) throws BadRequestException;
    Page<UserWithDetailsDto> getUsersWithDetails(String userID, String role, String query, PageDto pageDto) throws BadRequestException;
    UserWithDetailsDto createUserWithDetails(UserCreationDto userCreationDto, String parentID) throws BadRequestException;
    UserWithDetailsDto updateUserWithDetails(UserWithDetailsDto userCreationDto, String userID);
    void activeDeActiveUser(String userID, boolean status);
    void changePassword(String userID, PasswordChangeDto passwordChange, String process) throws BadRequestException;
}
