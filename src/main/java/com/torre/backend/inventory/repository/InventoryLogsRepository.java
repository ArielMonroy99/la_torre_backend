package com.torre.backend.inventory.repository;

import com.torre.backend.inventory.entities.InventoryLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryLogsRepository extends JpaRepository<InventoryLogs, Long> {
    @Query(value = "SELECT l from InventoryLogs l where (l.comment ilike  concat('%',:filter,'%') " +
                    "or l.item.name ilike  concat('%',:filter,'%') " +
                    "or l.user.username ilike  concat('%',:filter,'%') " +
                    "or l.user.name ilike  concat('%',:filter,'%')) ")
    Page<InventoryLogs> filterLogs(String filter, Pageable pageable);


    @Query(value = "SELECT l from InventoryLogs l where (l.comment ilike  concat('%',:filter,'%') " +
            "or l.item.name ilike  concat('%',:filter,'%') " +
            "or l.user.username ilike  concat('%',:filter,'%') " +
            "or l.user.name ilike  concat('%',:filter,'%')) " +
            "and l.item.id = :id ")
    Page<InventoryLogs> filterLogsByProductId(String filter, @Param("id") Long productId, Pageable pageable);
}
