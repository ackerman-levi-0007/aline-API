package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IUserDao;
import com.aline.aline.entities.User;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.User.UserDto;
import com.aline.aline.repositories.UserRepo;
import com.aline.aline.utilities.CommonUtils;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao implements IUserDao {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public UserDto createUser(User user, String parentID) {
        //Validate if the user details are already present or not
        if(checkEmailExists(user.getEmail())) throw new EntityExistsException("EmailID already exists!!!");

        //Set encoded password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Save user
        User savedUser = this.userRepo.save(user);

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(String userID, UserDto userDto) {
        User user = getUser(userID);

        if(!Objects.equals(user.getEmail(), userDto.getEmail()) && checkEmailExists(userDto.getEmail())){
            throw new EntityExistsException("Updated emailID already associated to different user!!!");
        }

        //User can only update name and email
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        User updatedUser = this.userRepo.save(user);

        return this.modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByID(String userID) {
        User user = getUser(userID);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUserByID(String userID) {
        User user = getUser(userID);
        this.userRepo.delete(user);
    }



    @Override
    public User findByEmailForLogin(String email) {
        return userRepo.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User", "emailID", email)
        );
    }

    @Override
    public void activeDeActiveUser(String userID, boolean status) {
        User user = getUser(userID);
        user.setStatus(status);
        this.userRepo.save(user);
    }

    @Override
    public void resetPassword(String userID, String currentPassword, String newPassword) throws BadRequestException {
        User user = getUser(userID);
        if(user.getPassword().equals(passwordEncoder.encode(currentPassword))){
            user.setPassword(passwordEncoder.encode(newPassword));
            this.userRepo.save(user);
        }
        else{
            throw new BadRequestException("current password is not correct");
        }
    }

    @Override
    public void forgotPassword(String userID, String newPassword) {
        User user = getUser(userID);
        user.setPassword(passwordEncoder.encode(newPassword));
        this.userRepo.save(user);
    }

    @Override
    public Page<UserDto> findUsersByFilter(List<String> userIDs, String role, String filter, Pageable pageable) {
        Query query = new Query();

        if(!userIDs.isEmpty()) query.addCriteria(Criteria.where("id").in(userIDs));
        if(!CommonUtils.isNullOrEmpty(role)) query.addCriteria(Criteria.where("role").is(role));
        if(!CommonUtils.isNullOrEmpty(filter)) query.addCriteria(Criteria.where("name").regex(filter, "i"));// i denotes case insensitive);

        long totalCount = this.mongoTemplate.count(query, User.class);
        query.with(pageable);

        List<User> userList = this.mongoTemplate.find(query, User.class);
        List<UserDto> userDtoList = userList.stream().map(x -> this.modelMapper.map(x, UserDto.class)).toList();

        return new PageImpl<>(
                userDtoList,
                pageable,
                totalCount);
    }

    /*****************************************************************************************
                                           Helpers
     ****************************************************************************************/

    public boolean checkEmailExists(String email){
        Optional<User> fetchedUser = this.userRepo.findByEmail(email);
        return fetchedUser.isPresent();
    }

    public User getUser(String userID){
        return this.userRepo.findById(userID).orElseThrow(() ->
                new ResourceNotFoundException("User", "userID", userID)
        );
    }
}
