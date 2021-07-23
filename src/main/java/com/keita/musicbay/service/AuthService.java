package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.JwtToken;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.UserRepository;
import com.keita.musicbay.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtProvider jwtProvider;


    public JwtToken login(String username,String password) {
        Optional<Customer> customer = customerRepository.findByUsername(username);

        if(customer.isPresent() && customer.get().getPassword().equals(password))
            return new JwtToken(username, jwtProvider.generate(customer.get()));

        return null;
    }

}
