package com.torre.backend.inventory.entities;

import com.torre.backend.authorization.entities.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull()
    @NotBlank()
    @Size(min = 3, max = 50)
    @Column(nullable = false,length = 50, name = "name")
    private String name;
    @NotNull()
    @NotBlank()
    @Size(min = 3, max = 100)
    @Column(nullable = true,length = 100,name = "description")
    private String description;
}
