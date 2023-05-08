package com.example.group6_schoolkit.Login;

public class UserClass {
    private String name;
    private String email;
    private String role;

    public UserClass(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
