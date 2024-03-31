package com.aline.aline.repositories;

import com.aline.aline.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends MongoRepository<User, String> {

    /**
     * Find User based on the Email
     */
    Optional<User> findByEmail(String email);

    Page<User> findByRole(String role, Pageable pageable);

    Page<User> findByRoleAndNameContaining(String role, String query, Pageable pageable);
}
