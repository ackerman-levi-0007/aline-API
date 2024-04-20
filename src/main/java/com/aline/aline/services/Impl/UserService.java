package com.aline.aline.services.Impl;

import com.aline.aline.dao.IClinicDoctorRelationshipDao;
import com.aline.aline.dao.IUserDao;
import com.aline.aline.dao.IUserDetailsDao;
import com.aline.aline.entities.User;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ForbiddenException;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.PageDto;
import com.aline.aline.payload.User.*;
import com.aline.aline.services.IUserService;
import com.aline.aline.utilities.CommonUtils;
import com.aline.aline.utilities.EnumUtils;
import com.aline.aline.utilities.PageUtils;
import com.aline.aline.utilities.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserDao userDao;
    private final IUserDetailsDao userDetailsDao;
    private final IClinicDoctorRelationshipDao clinicDoctorRelationshipDao;

    @Override
    public UserDto createUser(User user, String parentID) throws BadRequestException {

        if(user.getRole().contains(UserRole.ROLE_DOCTOR) && CommonUtils.isNullOrEmpty(parentID)){
            throw new BadRequestException("ROLE_DOCTOR must have parentID associated with it!!!");
        }

        UserDto savedUser = this.userDao.createUser(user, parentID);

        //Add clinic doctor relationship and status in ClinicDoctorRelationship
        //If clinic is created it will also be the doctor so for clinic it will also be a doctor
        if(user.getRole().contains(UserRole.ROLE_CLINIC)){
            this.clinicDoctorRelationshipDao.create(
                    savedUser.getId(), savedUser.getId(), true
            );
        }
        //Add clinic doctor relationship and status in ClinicDoctorRelationship
        else if(!CommonUtils.isNullOrEmpty(parentID) && user.getRole().contains(UserRole.ROLE_DOCTOR)){
            this.clinicDoctorRelationshipDao.create(
                    parentID, savedUser.getId(), true
            );
        }

        return savedUser;
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
    public Page<UserDto> getUsers(String userID, String role, String query, PageDto pageDto) throws BadRequestException {
        Pageable pageable = PageUtils.getPageableFromPageDto(pageDto);

        List<String> userIDs = getUserIDForUser(userID, role);

        Page<UserDto> userDtoList = null;
        if(CommonUtils.isNullOrEmpty(role) || EnumUtils.contains(UserRole.class, role)){
            userDtoList = this.userDao.findUsersByFilter(userIDs, role, query, pageable);
        }
        else{
            throw new BadRequestException("Incorrect role provided.");
        }

        return userDtoList;
    }

    @Override
    public Page<UserWithDetailsDto> getUsersWithDetails(String userID, String role, String query, PageDto pageDto) throws BadRequestException {

        Page<UserDto> userDtoList = getUsers(userID, role, query, pageDto);

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

    @Override
    public void activeDeActiveUser(String userID, boolean status) { this.userDao.activeDeActiveUser(userID, status); }

    @Override
    public void changePassword(String userID, PasswordChangeDto passwordChange, String process) throws BadRequestException {
        if(Objects.equals(passwordChange.getNewPassword(), passwordChange.getReEnterNewPassword())){
            if(Objects.equals(process, "reset")){
                this.userDao.resetPassword(userID, passwordChange.getCurrentPassword(), passwordChange.getNewPassword());
            } else if (Objects.equals(process, "forgot")) {
                this.userDao.forgotPassword(userID, passwordChange.getNewPassword());
            }
            else{
                throw new RuntimeException("Internal server error : wrong process mentioned. Please contact administrator.");
            }
        }
        else{
            throw new BadRequestException("New password does not matches the re-entered new password");
        }
    }


    /*****************************************************************************************
                                            Helpers
     ****************************************************************************************/

    public List<String> getUserIDForUser(String userID, String role) throws BadRequestException {
        List<String> userIDs = new ArrayList<>();
        String currentLoggedInUserID = Objects.requireNonNull(SecurityUtils.getCurrentUserUserID()).toString();
        UserDto currentUserDetails = userDao.getUserByID(currentLoggedInUserID);
        if(!CommonUtils.isNullOrEmpty(userID))userIDs.add(userID);

        if(currentUserDetails.getRole().contains(UserRole.ROLE_ADMIN)) return userIDs;

        /* throw forbidden exception if role is ROLE_ADMIN and currentLoggedInUser role is not ROLE_ADMIN */
        if(Objects.equals(role, UserRole.ROLE_ADMIN.toString())) throw new ForbiddenException("User does not have access to view the requested data!!!");

        if(currentUserDetails.getRole().contains(UserRole.ROLE_LAB)) return userIDs;

        if(currentUserDetails.getRole().contains(UserRole.ROLE_CLINIC)){
            if(Objects.equals(role, UserRole.ROLE_LAB.toString())) return userIDs;
            if(Objects.equals(role, UserRole.ROLE_CLINIC.toString())){
                if(CommonUtils.isNullOrEmpty(userID)){
                    userIDs.add(currentLoggedInUserID);
                    return userIDs;
                }
                else if(Objects.equals(userID, currentUserDetails.getId())){
                    return userIDs;
                }
                else{
                    throw new BadRequestException("The provided details are not correct. Please check !!!");
                }
            }
            if(Objects.equals(role, UserRole.ROLE_DOCTOR.toString())){
                if(CommonUtils.isNullOrEmpty(userID)){
                    userIDs = this.clinicDoctorRelationshipDao.getDoctorIdsForClinicId(currentLoggedInUserID);
                    return userIDs;
                }
                else{
                    if(this.clinicDoctorRelationshipDao.checkDoctorIDRelationshipToClinicID(userID, currentLoggedInUserID)){
                        return userIDs;
                    }
                    else{
                        throw new BadRequestException("Doctor ID " + userID + " is not mapped with the clinic");
                    }
                }
            }
        }

        if(currentUserDetails.getRole().contains(UserRole.ROLE_DOCTOR)){
            if(Objects.equals(role, UserRole.ROLE_CLINIC.toString())){
                if(CommonUtils.isNullOrEmpty(userID)){
                    userIDs = this.clinicDoctorRelationshipDao.getClinicIdsForDoctorID(currentLoggedInUserID);
                    return userIDs;
                }
                else{
                    if(this.clinicDoctorRelationshipDao.checkDoctorIDRelationshipToClinicID(currentLoggedInUserID, userID)){
                        return userIDs;
                    }
                    else{
                        throw new BadRequestException("Clinic ID " + userID + " is not associated with the doctor");
                    }
                }
            }
            if(Objects.equals(role, UserRole.ROLE_DOCTOR.toString())){
                if(CommonUtils.isNullOrEmpty(userID)){
                    userIDs.add(currentLoggedInUserID);
                    return userIDs;
                }
                else if(Objects.equals(userID, currentUserDetails.getId())){
                    return userIDs;
                }
                else{
                    throw new BadRequestException("The provided details are not correct. Please check !!!");
                }
            }
        }

        throw new ForbiddenException("User does not have access to view the requested data!!!");
    }
}
