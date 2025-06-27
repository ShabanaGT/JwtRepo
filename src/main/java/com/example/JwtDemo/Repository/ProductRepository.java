package com.example.JwtDemo.Repository;

import com.example.JwtDemo.Models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products,Integer> {
}
