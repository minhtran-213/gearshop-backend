package com.nashtech.minhtran.gearshop.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "roles", schema = "public",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")},
        indexes = {@Index(name = "role_name_index", columnList = "name")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(STRING)
    @Column(length = 20, nullable = false)
    @NotNull
    private ERole name;

}
