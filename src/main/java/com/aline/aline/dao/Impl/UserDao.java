package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.ClinicDoctorRelationship;
import com.aline.aline.entities.User;
import com.aline.aline.enums.UserRole;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.UserDto;
import com.aline.aline.repositories.ClinicDoctorRelationshipRepo;
import com.aline.aline.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserDao implements IUserDao {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final ClinicDoctorRelationshipRepo clinicDoctorRelationshipRepo;

    @Override
    public UserDto createUser(User user) {

        //Set encoded password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = this.userRepo.save(user);

        //Add clinic doctor relationship and status in ClinicDoctorRelationship
        //If clinic is created it will also be the doctor so for clinic it will also be a doctor
        if(user.getRole().contains(UserRole.ROLE_CLINIC)){
            ClinicDoctorRelationship clinicDoctorRelationship = new ClinicDoctorRelationship();
            clinicDoctorRelationship.setClinicID(savedUser.getId().toString());
            clinicDoctorRelationship.setDoctorID(savedUser.getId().toString());
            clinicDoctorRelationship.setStatus(true);
            this.clinicDoctorRelationshipRepo.save(clinicDoctorRelationship);
        }

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto createUser(User user, String parentID) {

        //Set encoded password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = this.userRepo.save(user);

        //Add clinic doctor relationship and status in ClinicDoctorRelationship
        if(user.getRole().contains(UserRole.ROLE_DOCTOR)){
            ClinicDoctorRelationship clinicDoctorRelationship = new ClinicDoctorRelationship();
            clinicDoctorRelationship.setClinicID(parentID);
            clinicDoctorRelationship.setDoctorID(savedUser.getId().toString());
            clinicDoctorRelationship.setStatus(true);
            this.clinicDoctorRelationshipRepo.save(clinicDoctorRelationship);
        }

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(String userID, UserDto userDto) {
        User user = this.userRepo.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User", "userID", userID)
        );
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());

        User updatedUser = this.userRepo.save(user);

        return this.modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByID(String userID) {
        User user = this.userRepo.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User", "userID", userID)
        );
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUserByID(String userID) {
        User user = this.userRepo.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User", "userID", userID)
        );
        this.userRepo.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = this.userRepo.findAll();
        return userList.stream().map(x -> this.modelMapper.map(x, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public User findByEmailForLogin(String email) {
        return userRepo.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User", "userID", email)
        );
    }
}
