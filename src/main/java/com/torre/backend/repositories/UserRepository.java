package com.torre.backend.repositories;

import com.torre.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {

    @Query(value = "Select User from User u where u.username = :username")
    Optional<User> findByUsername(String username);
}
