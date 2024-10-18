package com.torre.backend.inventory.repository;

import com.torre.backend.inventory.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("Select i.id,i.name,i.stock,i.minimumStock,i.type,i.unit,i.category " +
            " from Item i where (i.name ilike  '%:filter%') or (i.category.name ilike  '%:filter%')" +
            " or (i.unit ilike  '%:filter%') or ( cast(i.type as string ) ilike '%:filter%')")
    Page<Item> filterItems(String filter, Pageable pageable);
}
