package com.keita.musicbay.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class Registration implements Serializable {
    private UUID uuid;
    private String username,email,city,password,biography;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy/MM/dd")
    private LocalDate date;

    public Registration(){}

    @Builder
    public Registration(UUID uuid,String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
