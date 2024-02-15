package com.aline.aline.services;

import com.aline.aline.entities.User;
import com.aline.aline.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    UserDto createUser(User user);
    UserDto updateUser(String userID, UserDto userDto);
    UserDto getUserByID(String userID);
    void deleteUserByID(String userID);
    List<UserDto> getAllUsers();
}
