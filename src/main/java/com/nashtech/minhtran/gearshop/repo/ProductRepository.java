package com.nashtech.minhtran.gearshop.repo;

import com.nashtech.minhtran.gearshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
