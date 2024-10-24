package com.torre.backend.inventory.repository;

import com.torre.backend.inventory.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("Select c from Category c where c.name ilike  concat('%',:filter, '%') ")
    Page<Category> filterCategory (String filter, Pageable pageable);
}
