package com.example.JwtDemo.Services;

import com.example.JwtDemo.DTO.JwtDto;
import com.example.JwtDemo.Models.Users;
import com.example.JwtDemo.Repository.UserRepository;

import com.example.JwtDemo.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Serviceimpl implements  UserService{
    @Autowired
  UserRepository userrepo;
    @Autowired
    JwtUtil jwtutilObj;

    private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Override
    public String createUser(Users newUser) {

         String encodedPassword=passwordEncoder.encode(newUser.getPassword());
         newUser.setPassword(encodedPassword);
        userrepo.save(newUser);
         return "User registered successfully";
    }

    @Override
    public JwtDto signinUser(String email, String password) {
        Users user=userrepo.findByEmailAndPassword(email,password);
        if(user==null){
            return new JwtDto(email,password,"User Login failed");
        }
        String token=jwtutilObj.generateToken(user);
        return new JwtDto(email,password,token);
    }

    @Override
    public JwtDto signinWithHashing(String email,String password){
        Users user=userrepo.findByEmail(email);

        if(user==null && !passwordEncoder.matches(password,user.getPassword()) ){
            return new JwtDto(email,"","Login failee");
        }
        String token=jwtutilObj.generateToken(user);
        return new JwtDto(email,"",token);

    }

    @Override
    public List<Users> getAllUser() {
        return userrepo.findAll();
    }

    @Override
    public Users getUser(int id) {
        return userrepo.findById(id).orElseThrow(()->new RuntimeException("No user found"));
    }

    @Override
    public String updateUser(int id, Users editUser) {
        Optional<Users> existingUser=userrepo.findById(id);
        if(existingUser.isPresent()){
            Users user=existingUser.get();
            user.setName(editUser.getName());
            user.setEmail(editUser.getEmail());
            user.setPassword(editUser.getPassword());
            userrepo.save(user);
            return "User Updated successfully";
        }
        return  "User not found";
    }

    @Override
    public String deleteUser(int id) {
        Optional<Users> existingUser=userrepo.findById(id);
        if(existingUser.isPresent()){

            userrepo.deleteById(id);
            return "User deleted successfully";
        }
        return "No user found";

    }

    @Override
    public UserDetails findUser(String email) throws UsernameNotFoundException {
        Users user = userrepo.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User Not found");
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

}
