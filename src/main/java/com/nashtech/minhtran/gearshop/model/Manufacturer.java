package com.nashtech.minhtran.gearshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "manufacturer", schema = "public",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")},
        indexes = {@Index(name = "manufacturer_name_index", columnList = "name", unique = true)})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    private Collection<Product> products;
}
