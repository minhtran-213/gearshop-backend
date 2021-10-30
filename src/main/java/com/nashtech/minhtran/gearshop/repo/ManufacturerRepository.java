package com.nashtech.minhtran.gearshop.repo;

import com.nashtech.minhtran.gearshop.model.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    Page<Manufacturer> findByName(String name, Pageable pageable);
}
