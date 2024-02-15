package com.aline.aline.dao;

import com.aline.aline.entities.User;
import com.aline.aline.payload.UserDto;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserDao {
    UserDto createUser(User user);
    UserDto updateUser(String userID, UserDto userDto);
    UserDto getUserByID(String userID);
    void deleteUserByID(String userID);
    List<UserDto> getAllUsers();
}
