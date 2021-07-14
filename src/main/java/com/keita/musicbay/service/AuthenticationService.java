package com.keita.musicbay.service;

import com.keita.musicbay.repository.UserRepository;
import com.keita.musicbay.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;



    /*
        TODO
            -findByEmail()
            -check password
            -jwtProvider.generate(user)
            -return JWTtoken
     */
}
