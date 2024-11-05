package com.torre.backend.authorization.repositories;

import com.torre.backend.authorization.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("Select a from Role a where cast(a.role as string ) ilike :filter ")
    Page<Role> filterRole (String filter, Pageable pageable);
}