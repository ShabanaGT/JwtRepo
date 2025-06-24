package com.example.JwtDemo.DTO;

public class LoginDto {

    private String email;
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginDto() {
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim(); // helps avoid extra spaces
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.trim(); // helps avoid extra spaces
    }
}
