package com.aline.aline.dao.Impl;

import com.aline.aline.dao.IUserDetailsDao;
import com.aline.aline.entities.UserDetails;
import com.aline.aline.exceptionHandler.ResourceNotFoundException;
import com.aline.aline.payload.User.UserDetailsDto;
import com.aline.aline.repositories.UserDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDetailsDao implements IUserDetailsDao {
    private final UserDetailsRepo userDetailsRepo;
    private final ModelMapper modelMapper;
    @Override
    public List<UserDetailsDto> getUserDetailsByUserIDs(List<String> userIDs) {
        List<UserDetails> userDetails = this.userDetailsRepo.findByUserID(userIDs);
        return userDetails.stream().map(x -> this.modelMapper.map(x, UserDetailsDto.class)).toList();
    }

    @Override
    public UserDetailsDto getByUserID(String id) {
        UserDetails userDetails = this.userDetailsRepo.findByUserID(id).orElseThrow(() ->
                new ResourceNotFoundException("UserDetails", "userID", id)
        );
        return this.modelMapper.map(userDetails, UserDetailsDto.class);
    }

    @Override
    public UserDetailsDto createUserDetails(UserDetails userDetails) {
        UserDetails savedUserDetails = this.userDetailsRepo.save(userDetails);
        return this.modelMapper.map(savedUserDetails, UserDetailsDto.class);
    }
}
