package com.torre.backend.inventory.entities;

import com.torre.backend.authorization.entities.Auditable;
import com.torre.backend.inventory.enums.ItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "item")
@AllArgsConstructor
public class Item extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @Size(min = 1, max = 100)
    @NotBlank()
    @NotNull()
    private String name;
    @NotNull()
    @Column(nullable = false)
    private Integer stock;
    @NotNull()
    @Column(nullable = false, name = "minimum_stock")
    private Integer minimumStock;
    @NotNull()
    @Column(nullable = false)
    private String unit;
    @NotNull()
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @NotNull()
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    private Category category;

}
