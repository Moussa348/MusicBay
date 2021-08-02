package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.repository.CustomerRepository;
import com.keita.musicbay.repository.LikingRepository;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;


    public boolean createCustomer(Registration registration) throws Exception {
        if (!customerRepository.existsByEmail(registration.getEmail()) && !customerRepository.existsByUsername(registration.getUsername())) {
            Customer customer = new Customer(registration, userService.setDefaultProfilePicture());
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    public Profile updateCustomer(Registration registration) throws Exception {
        Customer customer = customerRepository.findById(registration.getUuid()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find customer with uuid : " + registration.getUuid()));


        return customer.getUsername().equals(registration.getUsername()) ||
                !customerRepository.existsByUsername(registration.getUsername()) ?
                new Profile(customerRepository.save(new Customer(registration, customer))) : null;
    }

    //TODO : add individual method to save picture


}
