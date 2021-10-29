package com.nashtech.minhtran.gearshop.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order", schema = "public",
        indexes = {
                @Index(name = "order_id_index", columnList = "id", unique = true),
                @Index(name = "order_userId_index", columnList = "userId")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "createdDate", nullable = false)
    private Date createdDate;

    @Column(name = "status")
    private boolean status;

    @Column(name = "expiredDate", nullable = false)
    private Date expiredDate;

    @ManyToOne
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

    @Column(name = "total")
    private double total;
}
