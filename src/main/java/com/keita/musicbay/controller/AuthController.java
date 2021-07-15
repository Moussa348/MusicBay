package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.JwtToken;
import com.keita.musicbay.service.AuthService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5001")
@Log
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public JwtToken login(@RequestParam("username")String username,@RequestParam("password")String password){
        log.info(String.valueOf(LocalDateTime.now().getDayOfMonth()));
        log.info(LocalDateTime.now().getDayOfWeek().toString());
        return authService.login(username,password);
    }
}
