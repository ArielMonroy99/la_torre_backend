package com.torre.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category extends Auditable{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 200)
    private String description;
}
