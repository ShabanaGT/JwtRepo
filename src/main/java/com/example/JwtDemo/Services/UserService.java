package com.example.JwtDemo.Services;

import com.example.JwtDemo.DTO.JwtDto;
import com.example.JwtDemo.Models.Users;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public String createUser(Users newUser);
    public JwtDto signinUser(String email, String password);
    public JwtDto signinWithHashing(String email, String password);
    public List<Users> getAllUser();
    public Users getUser(int id);
    public String updateUser(int id,Users editUser);
    public String deleteUser(int id);
    public UserDetails findUser(String email);
}
