package com.nashtech.minhtran.gearshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "rate", schema = "public",
        indexes = {
                @Index(name = "rate_id_index", columnList = "id", unique = true),
                @Index(name = "rate_comment_index", columnList = "comment"),
                @Index(name = "rate_userid_index", columnList = "comment"),
                @Index(name = "rate_productDetail_index", columnList = "comment"),
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private int id;

        @ManyToOne
        @JoinColumn(name = "userId", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "productDetailId", nullable = false)
        private ProductDetail productDetail;

        @Column(name = "point")
        @Min(value = 0)
        private double point;

        @Column(name = "comment")
        private String comment;

        @Column(name = "createdDate", nullable = false)
        private Date createdDate;
}
