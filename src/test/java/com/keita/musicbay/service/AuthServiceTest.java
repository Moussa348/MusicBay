package com.keita.musicbay.service;


import com.keita.musicbay.model.dto.JwtToken;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.UserRepository;
import com.keita.musicbay.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    JwtProvider jwtProvider;

    @InjectMocks
    AuthService authService;

    @Test
    void login(){
        //ARRANGE
        Customer customer = Customer.builder().username("bigBrr").password("bigBrr123").build();
        when(customerRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));
        when(jwtProvider.generate(customer)).thenReturn("bdf78sdjk34nj3o83242d2");

        String username = "adasdasd";
        String password = "dasdas";
        when(customerRepository.findByUsername(username)).thenReturn(Optional.empty());

        //ACT
        JwtToken jwtToken = authService.login(customer.getUsername(),customer.getPassword());
        JwtToken noToken = authService.login(username,password);
        //ASSERT
        assertNotNull(jwtToken);
        assertNull(noToken);
    }
}
