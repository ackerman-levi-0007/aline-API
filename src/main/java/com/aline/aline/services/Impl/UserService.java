package com.aline.aline.services.Impl;

import com.aline.aline.dao.IUserDao;
import com.aline.aline.dao.IUserDetailsDao;
import com.aline.aline.entities.User;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PageDto;
import com.aline.aline.payload.User.UserCreationDto;
import com.aline.aline.payload.User.UserDetailsDto;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.payload.User.UserWithDetailsDto;
import com.aline.aline.services.IUserService;
import com.aline.aline.utilities.CommonUtils;
import com.aline.aline.utilities.PageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserDao userDao;
    private final IUserDetailsDao userDetailsDao;

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
    public void deleteUserByID(String userID) {
        this.userDao.deleteUserByID(userID);
        this.userDetailsDao.deleteUserDetailsByUserID(userID);
    }

    @Override
    public Page<UserDto> getAllUsers(String role, String query, PageDto pageDto) throws BadRequestException {
        Pageable pageable = PageUtils.getPageableFromPageDto(pageDto);

        Page<UserDto> userDtoList = null;
        if(CommonUtils.isNullOrEmpty(role)){
            userDtoList = this.userDao.getAllUsers(pageable);
        }
        else{
            if(UserRole.contains(role)){
                userDtoList = this.userDao.getAllUsersByRole(role, query, pageable);
            }
            else{
                throw new BadRequestException("Incorrect role provided.");
            }
        }

        return userDtoList;
    }

    @Override
    public Page<UserWithDetailsDto> getAllUsersWithDetails(String role, String query, PageDto pageDto) throws BadRequestException {

        Pageable pageable = PageUtils.getPageableFromPageDto(pageDto);
        Page<UserDto> userDtoList = null;
        if(CommonUtils.isNullOrEmpty(role)){
            userDtoList = this.userDao.getAllUsers(pageable);
        }
        else{
            if(UserRole.contains(role)){
                userDtoList = this.userDao.getAllUsersByRole(role, query, pageable);
            }
            else{
                throw new BadRequestException("Incorrect role provided.");
            }
        }

        List<UserWithDetailsDto> userWithDetailsDtoList = userDtoList.stream().map(
                userDto ->{
                    UserDetailsDto userDetailsDto =  null;
                    try{
                        userDetailsDto = this.userDetailsDao.getByUserID(userDto.getId());
                    }
                    catch (ResourceNotFoundException ignored){

                    }
                    return new UserWithDetailsDto(userDto, userDetailsDto);
                }
        ).toList();

        return new PageImpl<>(userWithDetailsDtoList, userDtoList.getPageable(), userDtoList.getTotalElements());
    }

    @Override
    public UserWithDetailsDto createUserWithDetails(UserCreationDto userCreationDto, String parentID) throws BadRequestException {
        UserDto userDto = createUser(userCreationDto.getUser(), parentID);
        userCreationDto.getUserDetails().setUserID(userDto.getId());
        UserDetailsDto userDetailsDto = this.userDetailsDao.createUserDetails(userCreationDto.getUserDetails());
        return new UserWithDetailsDto(userDto, userDetailsDto);
    }

    @Override
    public UserWithDetailsDto updateUserWithDetails(UserWithDetailsDto userCreationDto, String userID) {
        UserDto userDto = this.userDao.updateUser(userID, userCreationDto.getUserDto());
        UserDetailsDto userDetailsDto = this.userDetailsDao.updateUserDetailsByUserID(userID, userCreationDto.getUserDetailsDto());
        return new UserWithDetailsDto(userDto, userDetailsDto);
    }
}
