package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.JwtToken;
import com.keita.musicbay.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public JwtToken login(@RequestParam("username")String username,@RequestParam("password")String password){
        return authenticationService.login(username,password);
    }
}
