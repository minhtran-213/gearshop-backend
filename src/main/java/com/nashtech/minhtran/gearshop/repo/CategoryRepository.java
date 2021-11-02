package com.nashtech.minhtran.gearshop.repo;

import com.nashtech.minhtran.gearshop.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findByName(String name, Pageable pageable);
    List<Category> findByCategory(Category category);

    @Query("SELECT c FROM Category c WHERE c.category IS NULL")
    List<Category> findWhereParentIsNull();
}
