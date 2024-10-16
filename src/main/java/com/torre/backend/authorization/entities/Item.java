package com.torre.backend.authorization.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static jakarta.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "item")
@Data
public class Item extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private Integer stock;
    @Column(name = "minimum_stock")
    private Integer minimumStock;
    @Column(name = "unit",length = 100, nullable = false)
    private String unit;
    @Column(name = "type",length = 50, nullable = false)
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
