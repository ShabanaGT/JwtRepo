package com.example.JwtDemo.Services;

import com.example.JwtDemo.DTO.JwtDto;
import com.example.JwtDemo.Models.Users;

import java.util.List;

public interface UserService {
    public String createUser(Users newUser);
    public JwtDto signinUser(String email, String password);
    public List<Users> getAllUser();
    public Users getUser(int id);
    public String updateUser(int id,Users editUser);
    public String deleteUser(int id);
}
