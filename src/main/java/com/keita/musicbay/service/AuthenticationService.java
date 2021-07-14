package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.JwtToken;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.repository.UserRepository;
import com.keita.musicbay.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;


    public JwtToken login(String username,String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent() && user.get().getPassword().equals(password))
            return new JwtToken(username, jwtProvider.generate(user.get()));

        return null;
    }

}
