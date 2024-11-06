package com.torre.backend.product.repository;

import com.torre.backend.product.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT a FROM Product a WHERE " +
            "(a.category.name ILIKE :filter OR :filter IS NULL) AND " +
            "(a.name ILIKE :productName OR :productName IS NULL) AND " +
            "(a.status = 'ACTIVE')")
    Page<Product> filterActivatedProducts(@Param("filter") String filter, @Param("productName") String productName, Pageable pageable);

    @Query("SELECT a FROM Product a WHERE " +
            "(a.category.name ILIKE :filter OR :filter IS NULL) AND " +
            "(a.name ILIKE :productName OR :productName IS NULL) ")
    Page<Product> filterProducts(@Param("filter") String filter, @Param("productName") String productName, Pageable pageable);
}

