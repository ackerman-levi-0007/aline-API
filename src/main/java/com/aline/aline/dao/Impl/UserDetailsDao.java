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
import java.util.Optional;

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
    public UserDetailsDto getByUserID(String userID) {
        UserDetails userDetails = this.userDetailsRepo.findByUserID(userID).orElseThrow(() ->
                new ResourceNotFoundException("UserDetails", "userID", userID)
        );
        return this.modelMapper.map(userDetails, UserDetailsDto.class);
    }

    @Override
    public UserDetailsDto createUserDetails(UserDetails userDetails) {
        UserDetails savedUserDetails = this.userDetailsRepo.save(userDetails);
        return this.modelMapper.map(savedUserDetails, UserDetailsDto.class);
    }

    @Override
    public UserDetailsDto updateUserDetailsByUserID(String userID, UserDetailsDto userDetailsDto) {
        Optional<UserDetails> fetchedUserDetails = this.userDetailsRepo.findByUserID(userID);

        if(fetchedUserDetails.isPresent()){
            UserDetails userDetails = fetchedUserDetails.get();

            userDetails.setUserAddress(userDetailsDto.getUserAddress());
            userDetails.setUserCity(userDetailsDto.getUserCity());
            userDetails.setUserCountry(userDetailsDto.getUserCountry());
            userDetails.setMobileNo(userDetailsDto.getMobileNo());

            UserDetails savedUserDetails = this.userDetailsRepo.save(userDetails);

            return this.modelMapper.map(savedUserDetails, UserDetailsDto.class);
        }
        else {
            //If no user details are found for the user create the details object
            return createUserDetails(this.modelMapper.map(userDetailsDto, UserDetails.class));
        }
    }

    @Override
    public void deleteUserDetailsByUserID(String userID) {
        Optional<UserDetails> userDetails = this.userDetailsRepo.findByUserID(userID);
        userDetails.ifPresent(this.userDetailsRepo::delete);
    }
}
