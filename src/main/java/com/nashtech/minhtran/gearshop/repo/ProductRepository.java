package com.nashtech.minhtran.gearshop.repo;

import com.nashtech.minhtran.gearshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByName(String name, Pageable pageable);
}
