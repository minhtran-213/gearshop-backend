package com.nashtech.minhtran.gearshop.repo;

import com.nashtech.minhtran.gearshop.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
