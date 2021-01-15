package com.example.demo.payload;

public class UsernameAlreadyExitstsRespne {
    private String username;

    public UsernameAlreadyExitstsRespne(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
