package com.nashtech.minhtran.gearshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "category", schema = "public",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")},
        indexes = {@Index(name = "category_name_index", columnList = "name", unique = true)})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parentCategory")
    private Category category;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Category> categories;


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Collection<Product> products;
}
