package com.nashtech.minhtran.gearshop.repo;

import com.nashtech.minhtran.gearshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail (String email);
    Boolean existsByEmail (String email);
    Page<User> findByFirstName(String firstName, Pageable pageable);
}
