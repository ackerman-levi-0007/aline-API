package com.aline.aline.repositories;

import com.aline.aline.entities.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends MongoRepository<UserDetails, String> {
}
