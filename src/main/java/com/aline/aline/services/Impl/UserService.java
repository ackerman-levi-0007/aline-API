package com.aline.aline.services.Impl;

import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.User;
import com.aline.aline.enums.UserRole;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.services.IUserService;
import com.aline.aline.utilities.CommonUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public UserDto createUser(User user, String parentID) throws BadRequestException {

        if(user.getRole().contains(UserRole.ROLE_DOCTOR) && CommonUtils.isNullOrEmpty(parentID)){
            throw new BadRequestException("ROLE_DOCTOR must have parentID associated with it!!!");
        }

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
