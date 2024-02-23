package com.aline.aline.services.Impl;

import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.User;
import com.aline.aline.payload.UserDto;
import com.aline.aline.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Override
    public UserDto createUser(User user) {
        return this.userDao.createUser(user);
    }

    @Override
    public UserDto createUser(User user, String parentID) {
        return this.userDao.createUser(user, parentID);
    }

    @Override
    public UserDto updateUser(String userID, UserDto userDto) {
        return this.userDao.updateUser(userID, userDto);
    }

    @Override
    public UserDto getUserByID(String userID) {
        return this.userDao.getUserByID(userID);
    }

    @Override
    public void deleteUserByID(String userID) { this.userDao.deleteUserByID(userID); }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userDao.getAllUsers();
    }
}
