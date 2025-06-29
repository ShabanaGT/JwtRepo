package com.example.JwtDemo.Controllers;

import com.example.JwtDemo.DTO.JwtDto;
import com.example.JwtDemo.DTO.LoginDto;
import com.example.JwtDemo.Models.Products;
import com.example.JwtDemo.Models.Users;
import com.example.JwtDemo.Repository.UserRepository;
import com.example.JwtDemo.Security.JwtUtil;
import com.example.JwtDemo.Services.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/ImageDir/Images";
    @Autowired
    UserService service;
    @Autowired
    JwtUtil jwtutilObj;

    @PostMapping("/addUser ")
    public String registerUser(@RequestBody Users newuser) {

        return service.createUser(newuser);
    }

    @PostMapping("/loginWithToken")
    public JwtDto tokenLoginMethod(@RequestBody LoginDto userObj) {
        return service.signinUser(userObj.getEmail(), userObj.getPassword());

    }

    @PostMapping("/loginHashing")
    public JwtDto loginWithHashing(@RequestBody JwtDto userObj) {
        return service.signinWithHashing(userObj.getEmail(), userObj.getPassword());
    }

    @PutMapping("/updateUser/{id}")
    public String updateUser(@RequestHeader("Authorization") String token,
                             @PathVariable int id,
                             @RequestBody Users updatedUser) {

        token = token.replace("Bearer ", "");


        int tokenId = jwtutilObj.extractUserId(token);

        if (tokenId != id) {
            return "Unauthorized to update this user";
        }

        return service.updateUser(id, updatedUser);
    }

    @GetMapping
    public List<Users> gettAllUsers() {
        return service.getAllUser();
    }

    @GetMapping("/getOne/{id}")
    public Users getOneUser(@PathVariable int id) {
        return service.getUser(id);
    }

    @GetMapping("/admin/dashboard")
    public String adminPage() {
        return "Welcome Admin!";
    }

    @GetMapping("/user/profile")
    public String userPage() {
        return "Welcome User!";
    }

    @PostMapping("/admin/addproductimage")
    public ResponseEntity<?> uploadProductImage(@RequestPart Products product,
                                                @RequestPart MultipartFile imageFile) {
        try {
            Products product1 = service.uploadProductImage(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/saveUserData")
    public String saveUserinDB(@ModelAttribute Users user, @RequestParam("profileImg") MultipartFile file) throws IOException {
        String originalFileNmae = file.getOriginalFilename();
        Path filenameAndPath = Paths.get(uploadDirectory, originalFileNmae);
        Files.write(filenameAndPath, file.getBytes());
        user.setPhoto(filenameAndPath.toString());

        return service.saveUserRecord(user);

    }

    @PostMapping("/getuserbyid/{id}")
    public ResponseEntity<Users> getById(@PathVariable int id) {
        Users user = service.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/getimage/{id}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable int id) throws IOException {
        Users user = service.getUserById(id);
        Path imagePath = Paths.get(uploadDirectory, user.getPhoto());
        FileSystemResource resource = new FileSystemResource(imagePath.toFile());
        String contentType = Files.probeContentType(imagePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                .body((Resource) resource);


    }
}
