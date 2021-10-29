package com.nashtech.minhtran.gearshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "orderDetail", schema = "public",
        indexes = {
        @Index(name = "orderDetail_id_index", columnList = "id", unique = true),
        @Index(name = "orderDetail_orderId_index", columnList = "orderId"),
        @Index(name = "orderDetail_productId_index", columnList = "productDetailId"),
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productDetailId", nullable = false)
    private ProductDetail productDetail;

    @Column(name = "quantity", nullable = false)
    @Min(value = 0)
    private int quantity;

    @Column(name = "price", nullable = false)
    @Min(value = 0)
    private double price;
}
