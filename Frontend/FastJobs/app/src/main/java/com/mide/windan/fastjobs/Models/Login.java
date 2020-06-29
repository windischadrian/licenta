package com.mide.windan.fastjobs.Models;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Login {
    private String username;
    private String password;

    public Login(String username, String password){
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        final String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        this.password = hashedPassword;
    }
}
