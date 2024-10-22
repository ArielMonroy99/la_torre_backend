package com.torre.backend.authorization.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "casbin_rule")
@AllArgsConstructor
@NoArgsConstructor
public class CasbinRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "ptype", nullable = false, length = 100)
    private String ptype;

    @Size(max = 100)
    @Column(name = "v0", length = 100)
    private String v0;

    @Size(max = 100)
    @Column(name = "v1", length = 100)
    private String v1;

    @Size(max = 100)
    @Column(name = "v2", length = 100)
    private String v2;

    @Size(max = 100)
    @Column(name = "v3", length = 100)
    private String v3;

    @Size(max = 100)
    @Column(name = "v4", length = 100)
    private String v4;

    @Size(max = 100)
    @Column(name = "v5", length = 100)
    private String v5;

    public CasbinRule(String ptype, String subject, String object, String actions) {
        this.ptype = ptype;
        this.v0 = subject;
        this.v1 = object;
        this.v2 = actions;
    }

    @Override
    public String toString() {
        return "CasbinRule{" +
                " object:" + v0 +
                " subject:" + v1 +
                " action:" + v2 +"}";
    }
}