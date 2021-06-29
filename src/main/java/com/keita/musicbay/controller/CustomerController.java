package com.keita.musicbay.controller;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.service.CustomerService;
import com.keita.musicbay.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TextService textService;

    @PostMapping("/createCustomer")
    public boolean createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @PostMapping("/createMessage")
    public String createMessage(@RequestBody TextDTO message){
        return textService.createMessage(message);
    }

    @GetMapping("/getProfile/{username}")
    public Profile getProfile(@PathVariable String username){
        return customerService.getProfile(username);
    }

    @GetMapping("/getListLikedMusic/{username}")
    public List<LikedMusic> getListLikedMusic(@PathVariable String username){
        return customerService.getListLikedMusic(username);
    }

    @GetMapping("/getListSharedMusic/{username}")
    public List<SharedMusic> getListSharedMusic(@PathVariable String username){
        return customerService.getListSharedMusic(username);
    }

    @GetMapping("/getListPurchasedMusic/{username}")
    public List<PurchasedMusic> getListPurchasedMusic(@PathVariable String username){
        return customerService.getListPurchasedMusic(username);
    }
}
