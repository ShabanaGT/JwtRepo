package com.example.JwtDemo.Repository;

import com.example.JwtDemo.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer> {
    Users findByEmailAndPassword(String email, String password);
    Users findByEmail(String email);

}
