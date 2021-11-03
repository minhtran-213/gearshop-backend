package com.nashtech.minhtran.gearshop.repo;

import com.nashtech.minhtran.gearshop.model.Address;
import com.nashtech.minhtran.gearshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUser(User user);
}
