package com.nashtech.minhtran.gearshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "productDetail", schema = "public",
        indexes = {@Index(name = "productDetail_id_index", columnList = "id", unique = true)})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "color")
    private String color;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "size")
    private String size;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Rate> rates;

    @OneToMany(mappedBy = "productDetail", fetch = FetchType.LAZY)
    private Collection<OrderDetail> orderDetails;
}
