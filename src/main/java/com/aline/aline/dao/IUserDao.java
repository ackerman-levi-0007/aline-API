package com.aline.aline.dao;

import com.aline.aline.entities.User;
import com.aline.aline.payload.User.UserDto;
import org.apache.coyote.BadRequestException;
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
    User findByEmailForLogin(String email);
    void activeDeActiveUser(String userID, boolean status);
    void resetPassword(String userID, String currentPassword, String newPassword) throws BadRequestException;
    void forgotPassword(String userID, String newPassword);
    Page<UserDto> findUsersByFilter(List<String> userIDs, String role, String query, Pageable pageable);
}
