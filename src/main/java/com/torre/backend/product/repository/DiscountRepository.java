package com.torre.backend.product.repository;

import com.torre.backend.product.entities.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

  @Query("SELECT d FROM Discount d JOIN d.products p WHERE (p.name ilike CONCAT('%',:filter,'%') or p.category.name ilike CONCAT('%',:filter,'%'))")
  Page<Discount> filterDiscounts(Pageable pageable, String filter);

  @Query("SELECT d FROM Discount d JOIN d.products p WHERE p.id = :id and d.startDate < CURRENT_TIMESTAMP and d.endDate > CURRENT_TIMESTAMP and d.status = 'ACTIVE' order by d.createdAt")
  Discount findDiscountByProductId(Long id);

  @Query("SELECT d FROM Discount d JOIN d.products p WHERE d.code = :code and d.startDate < CURRENT_TIMESTAMP and d.endDate > CURRENT_TIMESTAMP and d.status = 'ACTIVE' order by d.createdAt")
  Discount findDiscountByCode(String code);
}
