package com.torre.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_torre")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Auditable{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
}
