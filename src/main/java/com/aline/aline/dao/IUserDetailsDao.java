package com.aline.aline.dao;

import com.aline.aline.entities.UserDetails;
import com.aline.aline.payload.User.UserDetailsDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDetailsDao {
    List<UserDetailsDto> getUserDetailsByUserIDs(List<String> userIDs);

    UserDetailsDto getByUserID(String id);

    UserDetailsDto createUserDetails(UserDetails userDetails);
}
