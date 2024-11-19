package com.torre.backend.inventory.entities;

import com.torre.backend.authorization.entities.Auditable;
import com.torre.backend.authorization.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class InventoryLogs extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 1000)
    private String comment;
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private User user;
    @JoinColumn(referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Item item;
}
