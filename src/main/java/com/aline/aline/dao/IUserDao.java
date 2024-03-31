package com.aline.aline.dao;

import com.aline.aline.entities.User;
import com.aline.aline.payload.User.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IUserDao {
    UserDto createUser(User user, String parentID);
    UserDto updateUser(String userID, UserDto userDto);
    UserDto getUserByID(String userID);
    void deleteUserByID(String userID);
    List<UserDto> getAllUsers();
    Page<UserDto> getAllUsers(Pageable pageRequest);
    User findByEmailForLogin(String email);
    Page<UserDto> getAllUsersByRole(String role, String query, Pageable pageable);
}
