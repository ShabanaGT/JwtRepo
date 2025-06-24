package com.example.JwtDemo.Controllers;

import com.example.JwtDemo.DTO.JwtDto;
import com.example.JwtDemo.DTO.LoginDto;
import com.example.JwtDemo.Models.Users;
import com.example.JwtDemo.Repository.UserRepository;
import com.example.JwtDemo.Security.JwtUtil;
import com.example.JwtDemo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {
   @Autowired
    UserService service;
   @Autowired
    JwtUtil jwtutilObj;
    @PostMapping("/createUser")
    public String registerUser(@RequestBody Users newuser) {
        return service.createUser(newuser);
    }

    @PostMapping("/loginWithToken")
    public JwtDto tokenLoginMethod(@RequestBody LoginDto userObj){
        return service.signinUser(userObj.getEmail(),userObj.getPassword());

    }

    @PutMapping("/updateUser/{id}")
    public String updateUser(@RequestHeader("Authorization") String token,
                             @PathVariable int id,
                             @RequestBody Users updatedUser) {

        token = token.replace("Bearer ", "");


        int tokenId=jwtutilObj.extractUserId(token);

        if (tokenId != id) {
            return "Unauthorized to update this user";
        }

        return service.updateUser(id, updatedUser);
    }

    @GetMapping("/getOne/{id}")
    public Users getOneUser(@PathVariable int id){
        return service.getUser(id);
    }





}
