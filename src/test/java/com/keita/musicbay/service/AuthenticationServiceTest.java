package com.keita.musicbay.service;


import com.keita.musicbay.model.dto.JwtToken;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.repository.UserRepository;
import com.keita.musicbay.security.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    JwtProvider jwtProvider;

    @InjectMocks
    AuthenticationService authenticationService;

    @Test
    void login(){
        //ARRANGE
        User user = Customer.builder().username("bigBrr").password("bigBrr123").build();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(jwtProvider.generate(user)).thenReturn("bdf78sdjk34nj3o83242d2");

        String username = "adasdasd";
        String password = "dasdas";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        //ACT
        JwtToken jwtToken = authenticationService.login(user.getUsername(),user.getPassword());
        JwtToken noToken = authenticationService.login(username,password);
        //ASSERT
        assertNotNull(jwtToken);
        assertNull(noToken);
    }
}
