package com.example.demo.crud.repository;


import com.example.demo.crud.entity.Category;
import com.example.demo.crud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("SELECT o from Product o where o.category =?1")
    List<Product> findByCategoryId(Category category);
}
