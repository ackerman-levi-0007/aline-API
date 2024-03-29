package com.aline.aline.repositories;

import com.aline.aline.entities.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsRepo extends MongoRepository<UserDetails, String> {
    List<UserDetails> findByUserID(List<String> userIDs);
    Optional<UserDetails> findByUserID(String id);
}
