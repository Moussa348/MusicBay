package com.keita.musicbay.controller;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.dto.*;
import com.keita.musicbay.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public boolean createCustomer(Customer customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping("/getProfile/{username}")
    public Profile getProfile(@PathVariable String username){
        return customerService.getProfile(username);
    }

    @PostMapping("/follow")
    public void follow(@RequestParam("username")String username, @RequestParam("usernameToFollow") String usernameToFollow){
         customerService.follow(username,usernameToFollow);
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

    @GetMapping("/getListFollower/{username}")
    public List<Follower> getListFollower(@PathVariable String username){
        return customerService.getListFollower(username);
    }
}
