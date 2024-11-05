package com.torre.backend.authorization.repositories;

import com.torre.backend.authorization.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    @Query("Select a from User a where  cast(a.role.role as string ) ilike :filter ")
    Page<User> filterUser (String filter, Pageable pageable);
}
