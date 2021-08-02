package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.service.CustomerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:5001")
@Log
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/createCustomer")
    public boolean createCustomer(@RequestBody Registration registration) throws Exception{
        return customerService.createCustomer(registration);
    }

    @PatchMapping( "/updateCustomer")
    public Profile updateCustomer(@RequestBody Registration registration) {
        try {
            return customerService.updateCustomer(registration);
        } catch (Exception e) {
            log.warning(e.getMessage().toUpperCase());
            return null;
        }
    }
}
