package com.nashtech.minhtran.gearshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "product", schema = "public",
        indexes = {
        @Index(name = "product_id_index", columnList = "id", unique = true),
        @Index(name = "product_name_index", columnList = "name", unique = true),
                @Index(name = "product_category_index", columnList = "categoryId")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "description")
        private String description;

        @ManyToOne
        @JoinColumn(name = "manufacturerId", nullable = false)
        private Manufacturer manufacturer;

        @ManyToOne
        @JoinColumn(name = "categoryId", nullable = false)
        private Category category;

        @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonIgnore
        private Collection<ProductDetail> productDetails;

        @Column(name = "createdDate", nullable = false)
        private Date createdDate;

        @Column(name = "updatedDate")
        private Date updatedDate;
}
