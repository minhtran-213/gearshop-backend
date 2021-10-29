package com.nashtech.minhtran.gearshop.repo;

import com.nashtech.minhtran.gearshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
