package com.keita.musicbay.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtToken implements Serializable {
    private String username;
    private String token;

    public JwtToken(){}

    public JwtToken(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
